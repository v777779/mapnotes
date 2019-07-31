package ru.vpcb.map.notes.di.activity.splash;

import dagger.Subcomponent;
import ru.vpcb.map.notes.activity.splash.SplashActivity;

@Subcomponent
public interface  SplashComponent {

    void inject(SplashActivity activity);
}
