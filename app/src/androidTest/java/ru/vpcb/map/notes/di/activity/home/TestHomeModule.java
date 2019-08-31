package ru.vpcb.map.notes.di.activity.home;

import android.app.Activity;

import ru.vpcb.map.notes.activity.home.HomeMvpPresenter;
import ru.vpcb.map.notes.activity.home.HomePresenter;
import ru.vpcb.map.notes.data.formatter.LocationFormatter;
import ru.vpcb.map.notes.data.provider.LocationProvider;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.executors.IAppExecutors;
import ru.vpcb.map.notes.fragments.map.MapFragment;
import ru.vpcb.map.notes.manager.FAManager;


public class TestHomeModule extends HomeModule {
    private Activity activity;
    private LocationProvider locationProvider;
    private LocationFormatter locationFormatter;
    private MapFragment mapFragment;
    private FAManager analyticsManager;

    public TestHomeModule(Activity activity, LocationProvider locationProvider,
                          LocationFormatter locationFormatter, MapFragment mapFragment,
                          FAManager analyticsManager) {
        super(activity);
        this.activity = activity;
        this.locationProvider = locationProvider;
        this.locationFormatter = locationFormatter;
        this.mapFragment = mapFragment;
        this.analyticsManager = analyticsManager;
    }

    @Override
    HomeMvpPresenter provideHomeMvpPresenter(IAppExecutors appExecutors, UserRepository userRepository) {
        return new HomePresenter(appExecutors, userRepository);
    }

    @Override
    MapFragment provideMapFragment() {
        return mapFragment;
    }


    @Override
    LocationProvider provideAddressLocationProvider() {
        return locationProvider;
    }

    @Override
    LocationFormatter provideLocationFormatter(Activity activity) {
        return locationFormatter;
    }

    @Override
    FAManager provideFAManager() {
        return analyticsManager;
    }
}
