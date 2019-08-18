package ru.vpcb.map.notes.di.activity.splash;

import dagger.Subcomponent;
import ru.vpcb.map.notes.activity.splash.SplashActivity;
import ru.vpcb.map.notes.di.activity.ActivityScope;

@ActivityScope
@Subcomponent(modules = SplashModule.class)
public interface  SplashComponent {

    void inject(SplashActivity activity);
}
