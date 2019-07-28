package ru.vpcb.test.map.di.activity.home;

import dagger.Subcomponent;
import ru.vpcb.test.map.activity.home.HomeActivity;
import ru.vpcb.test.map.di.activity.ActivityScope;
import ru.vpcb.test.map.map.GoogleMapFragment;

@ActivityScope
@Subcomponent(modules = HomeModule.class)
public interface HomeComponent {

    void  inject(HomeActivity activity);
    void inject(GoogleMapFragment fragment);
}
