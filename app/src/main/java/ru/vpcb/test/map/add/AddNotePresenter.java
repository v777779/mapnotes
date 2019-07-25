package ru.vpcb.test.map.add;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import ru.vpcb.test.map.base.ScopedPresenter;
import ru.vpcb.test.map.data.Result;
import ru.vpcb.test.map.data.formatter.LocationFormatter;
import ru.vpcb.test.map.data.provider.LocationProvider;
import ru.vpcb.test.map.data.repository.NotesRepository;
import ru.vpcb.test.map.data.repository.UserRepository;
import ru.vpcb.test.map.executors.AppExecutors;
import ru.vpcb.test.map.executors.IListener;
import ru.vpcb.test.map.model.AuthUser;
import ru.vpcb.test.map.model.Location;
import ru.vpcb.test.map.model.Note;

public class AddNotePresenter extends ScopedPresenter<AddNoteView> implements AddNoteMvpPresenter {

    // TODO by inject
    private AppExecutors appExecutors;
    private UserRepository userRepository;
    private NotesRepository notesRepository;
    private LocationProvider locationProvider;
    private LocationFormatter locationFormatter;

    private AddNoteView view;
    private Location lastLocation;
    private String uid;


    public AddNotePresenter(AppExecutors appExecutors, UserRepository userRepository,
                            NotesRepository notesRepository, LocationProvider locationProvider,
                            LocationFormatter locationFormatter) {
        this.appExecutors = appExecutors;
        this.userRepository = userRepository;
        this.notesRepository = notesRepository;
        this.locationProvider = locationProvider;
        this.locationFormatter = locationFormatter;

        this.view = null;
        this.lastLocation = null;
        this.uid = null;
    }

    @Override
    public void onAttach(@NonNull AddNoteView view) {
        super.onAttach(view);
        this.view = view;

        locationProvider.startLocationUpdates();
// TODO launch
        AppExecutors userExecutors = new AppExecutors() {
            @Override
            public <T>void resume(Result<T> result) {
                if (result instanceof Result.Success) {
                    uid = ((AuthUser)result.getData()).getUid();
                }
            }
        };
// TODO launch
        userRepository.setExecutors(userExecutors);
        Result<AuthUser> userResult = userRepository.getCurrentUser();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (view == null) return;
        locationProvider.stopLocationUpdates();
        this.view = null;
    }

    @Override
    public void getCurrentLocation() {
        locationProvider.addUpdatableLocationListener(new IListener<Location>() {
            @Override
            public void invoke(Location location) {
                if (view != null) view.displayCurrentLocation(locationFormatter.format(location));
                lastLocation = location;
            }
        });
    }

    @Override
    public void addNote(String text) {
        if (view == null) return;

        view.clearNoteText();
        view.hideKeyboard();
        if (uid == null) return;

        double latitude = (lastLocation != null) ? lastLocation.getLatitude() : 0.0;
        double longitude = lastLocation != null ? lastLocation.getLongitude() : 0.0;
        Note note = new Note(latitude, longitude, text, uid);
        notesRepository.addNote(note);

    }

// methods

    @VisibleForTesting
    void updateLastLocation(Location location) {
        lastLocation = location;
    }


}
