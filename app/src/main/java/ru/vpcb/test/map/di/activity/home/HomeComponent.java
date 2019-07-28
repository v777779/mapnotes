package ru.vpcb.test.map.di.activity.home;

import dagger.Subcomponent;
import ru.vpcb.test.map.activity.home.HomeActivity;
import ru.vpcb.test.map.di.activity.ActivityScope;

@ActivityScope
@Subcomponent(modules = HomeModule.class)
public interface HomeComponent {

    void  inject(HomeActivity activity);

}
