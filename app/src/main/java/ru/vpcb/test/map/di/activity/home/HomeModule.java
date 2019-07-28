package ru.vpcb.test.map.di.activity.home;

import androidx.appcompat.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;
import ru.vpcb.test.map.activity.home.HomeMvpPresenter;
import ru.vpcb.test.map.activity.home.HomePresenter;
import ru.vpcb.test.map.data.provider.AddressLocationProvider;
import ru.vpcb.test.map.data.provider.LocationProvider;
import ru.vpcb.test.map.data.repository.UserRepository;
import ru.vpcb.test.map.di.activity.ActivityScope;
import ru.vpcb.test.map.executors.IAppExecutors;
import ru.vpcb.test.map.map.GeneralMapFragment;
import ru.vpcb.test.map.map.GoogleMapPresenter;
import ru.vpcb.test.map.map.MapFragment;
import ru.vpcb.test.map.map.MapMvpPresenter;

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
