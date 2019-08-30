package ru.vpcb.map.notes.di.activity.home;

import android.app.Activity;
import android.location.Geocoder;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;
import ru.vpcb.map.notes.FakeMapFragment;
import ru.vpcb.map.notes.activity.home.HomeMvpPresenter;
import ru.vpcb.map.notes.activity.home.HomePresenter;
import ru.vpcb.map.notes.data.formatter.FullAddressFormatter;
import ru.vpcb.map.notes.data.formatter.LocationFormatter;
import ru.vpcb.map.notes.data.provider.AddressLocationProvider;
import ru.vpcb.map.notes.data.provider.LocationProvider;
import ru.vpcb.map.notes.data.repository.NotesRepository;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.di.activity.ActivityScope;
import ru.vpcb.map.notes.di.activity.home.HomeModule;
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


public class TestHomeModule extends HomeModule {
    private Activity activity;

    public TestHomeModule(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    HomeMvpPresenter provideHomeMvpPresenter(IAppExecutors appExecutors, UserRepository userRepository) {
        return new HomePresenter(appExecutors, userRepository);
    }


    @Override
    MapFragment provideMapFragment() {
        return new FakeMapFragment();
    }


    @Override
    LocationProvider provideAddressLocationProvider() {
        return Mockito.mock(LocationProvider.class);
    }

    @Override
    LocationFormatter provideLocationFormatter(Activity activity) {
        return Mockito.mock(LocationFormatter.class);
    }

    @Override
    FAManager provideFAManager() {
        return new FAManager(activity);
    }
}
