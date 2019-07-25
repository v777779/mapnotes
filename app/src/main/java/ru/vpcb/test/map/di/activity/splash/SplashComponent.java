package ru.vpcb.test.map.di.activity.splash;

import dagger.Subcomponent;
import ru.vpcb.test.map.activity.splash.SplashActivity;

@Subcomponent
public interface  SplashComponent {

    void inject(SplashActivity activity);
}
