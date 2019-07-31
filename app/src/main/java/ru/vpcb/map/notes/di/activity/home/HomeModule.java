package ru.vpcb.map.notes.di.activity.home;

import androidx.appcompat.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;
import ru.vpcb.map.notes.activity.home.HomeMvpPresenter;
import ru.vpcb.map.notes.activity.home.HomePresenter;
import ru.vpcb.map.notes.data.provider.AddressLocationProvider;
import ru.vpcb.map.notes.data.provider.LocationProvider;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.di.activity.ActivityScope;
import ru.vpcb.map.notes.executors.IAppExecutors;
import ru.vpcb.map.notes.map.GeneralMapFragment;
import ru.vpcb.map.notes.map.GoogleMapPresenter;
import ru.vpcb.map.notes.map.MapFragment;
import ru.vpcb.map.notes.map.MapMvpPresenter;

@Module
public class HomeModule {
    private AppCompatActivity activity;

    public HomeModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    HomeMvpPresenter provideHomeMvpPresenter(IAppExecutors appExecutors, UserRepository userRepository) {
        return new HomePresenter(appExecutors, userRepository);
    }

    @Provides
    @ActivityScope
    MapMvpPresenter provideMapMvpPresenter() {
        return new GoogleMapPresenter();
    }

    @Provides
    @ActivityScope
    MapFragment provideMapFragment() {
        return new GeneralMapFragment();
    }


    @Provides
    @ActivityScope
    LocationProvider provideAddressLocationProvider() {
        return new AddressLocationProvider(activity, AddressLocationProvider.REQUEST_INTERVAL);
    }

}
