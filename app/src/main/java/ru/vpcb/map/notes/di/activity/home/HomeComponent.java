package ru.vpcb.map.notes.di.activity.home;

import dagger.Subcomponent;
import ru.vpcb.map.notes.activity.home.HomeActivity;
import ru.vpcb.map.notes.di.activity.ActivityScope;
import ru.vpcb.map.notes.map.GoogleMapFragment;

@ActivityScope
@Subcomponent(modules = HomeModule.class)
public interface HomeComponent {

    void  inject(HomeActivity activity);
    void inject(GoogleMapFragment fragment);
}
