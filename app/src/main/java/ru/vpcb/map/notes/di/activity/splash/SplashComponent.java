package ru.vpcb.map.notes.di.activity.splash;

import dagger.Subcomponent;
import ru.vpcb.map.notes.activity.splash.SplashActivity;
import ru.vpcb.map.notes.di.ActivityComponent;
import ru.vpcb.map.notes.di.ActivityComponentBuilder;
import ru.vpcb.map.notes.di.activity.ActivityScope;

@ActivityScope
@Subcomponent(modules = SplashModule.class)
public interface  SplashComponent extends ActivityComponent<SplashActivity> {

    @Subcomponent.Builder
    interface Builder extends ActivityComponentBuilder<SplashComponent, SplashModule> {

    }
}
