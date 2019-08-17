package ru.vpcb.map.notes.add;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import ru.vpcb.map.notes.base.ScopedPresenter;
import ru.vpcb.map.notes.data.Result;
import ru.vpcb.map.notes.data.formatter.LocationFormatter;
import ru.vpcb.map.notes.data.provider.LocationProvider;
import ru.vpcb.map.notes.data.repository.NotesRepository;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.executors.IConsumer;
import ru.vpcb.map.notes.model.AuthUser;
import ru.vpcb.map.notes.model.Location;
import ru.vpcb.map.notes.model.Note;

public class AddNotePresenter extends ScopedPresenter<AddNoteView> implements AddNoteMvpPresenter {

    private UserRepository userRepository;
    private NotesRepository notesRepository;
    private LocationProvider locationProvider;
    private LocationFormatter locationFormatter;

    private AddNoteView view;
    private Location lastLocation;
    private String uid;

    public AddNotePresenter(UserRepository userRepository,
                            NotesRepository notesRepository, LocationProvider locationProvider,
                            LocationFormatter locationFormatter) {
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
        Result<AuthUser> userResult = userRepository.getCurrentUser();
        if (userResult instanceof Result.Success) {
            uid = (userResult.getData()).getUid();
        }
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
        locationProvider.addUpdatableLocationListener(new IConsumer<Location>() {
            @Override
            public void accept(Location location) {
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
        view.displayCurrentLocation(note);
    }

// methods

    @VisibleForTesting
    void updateLastLocation(Location location) {
        lastLocation = location;
    }


}
