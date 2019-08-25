package ru.vpcb.map.notes.di.activity.home;

import android.app.Activity;
import android.location.Geocoder;

import dagger.Module;
import dagger.Provides;
import ru.vpcb.map.notes.activity.home.HomeMvpPresenter;
import ru.vpcb.map.notes.activity.home.HomePresenter;
import ru.vpcb.map.notes.data.formatter.FullAddressFormatter;
import ru.vpcb.map.notes.data.formatter.LocationFormatter;
import ru.vpcb.map.notes.data.provider.AddressLocationProvider;
import ru.vpcb.map.notes.data.provider.LocationProvider;
import ru.vpcb.map.notes.data.repository.NotesRepository;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.di.activity.ActivityScope;
import ru.vpcb.map.notes.executors.IAppExecutors;
import ru.vpcb.map.notes.fragments.add.AddNoteMvpPresenter;
import ru.vpcb.map.notes.fragments.add.AddNotePresenter;
import ru.vpcb.map.notes.fragments.map.GeneralMapFragment;
import ru.vpcb.map.notes.fragments.map.GoogleMapPresenter;
import ru.vpcb.map.notes.fragments.map.MapFragment;
import ru.vpcb.map.notes.fragments.map.MapMvpPresenter;
import ru.vpcb.map.notes.fragments.search.SearchNotesMvpPresenter;
import ru.vpcb.map.notes.fragments.search.SearchNotesPresenter;
import ru.vpcb.map.notes.manager.FAManager;

@Module
public class HomeModule {
    private Activity activity;

    public HomeModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    Activity provideActivity() {
        return activity;
    }

    @Provides
    @ActivityScope
    HomeMvpPresenter provideHomeMvpPresenter(IAppExecutors appExecutors, UserRepository userRepository) {
        return new HomePresenter(appExecutors, userRepository);
    }

    @Provides
    @ActivityScope
    MapMvpPresenter provideMapMvpPresenter(LocationProvider locationProvider) {
        return new GoogleMapPresenter(locationProvider);
    }

    @Provides
    @ActivityScope
    MapFragment provideMapFragment() {
        return new GeneralMapFragment();
    }

    @Provides
    @ActivityScope
    SearchNotesMvpPresenter provideSearchNotesMvpPresenter(IAppExecutors appExecutors,
                                                           UserRepository userRepository,
                                                           NotesRepository notesRepository) {
        return new SearchNotesPresenter(appExecutors, userRepository, notesRepository);
    }

    @Provides
    @ActivityScope
    LocationProvider provideAddressLocationProvider() {
        return new AddressLocationProvider(activity, AddressLocationProvider.REQUEST_INTERVAL);
    }

    @Provides
    @ActivityScope
    LocationFormatter provideLocationFormatter(Activity activity) {
        return new FullAddressFormatter(new Geocoder(activity));
    }

    @Provides
    @ActivityScope
    AddNoteMvpPresenter provideAddNoteMvpPresenter(UserRepository userRepository,
                                                   NotesRepository notesRepository,
                                                   LocationProvider locationProvider,
                                                   LocationFormatter locationFormatter) {
        return new AddNotePresenter(userRepository, notesRepository,
                locationProvider, locationFormatter);
    }


    @Provides
    @ActivityScope
    FAManager provideFAManager() {
        return new FAManager(activity);
    }
}
