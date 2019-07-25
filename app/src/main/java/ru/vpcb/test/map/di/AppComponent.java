package ru.vpcb.test.map.di;


import javax.inject.Singleton;

import dagger.Component;
import ru.vpcb.test.map.di.activity.splash.SplashComponent;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    SplashComponent getSplashComponent();

}
