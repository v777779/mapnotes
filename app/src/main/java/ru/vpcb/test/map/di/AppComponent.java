package ru.vpcb.test.map.di;


import javax.inject.Singleton;

import dagger.Component;
import ru.vpcb.test.map.di.activity.home.HomeComponent;
import ru.vpcb.test.map.di.activity.home.HomeModule;
import ru.vpcb.test.map.di.activity.login.signin.SignInComponent;
import ru.vpcb.test.map.di.activity.login.signup.SignUpComponent;
import ru.vpcb.test.map.di.activity.splash.SplashComponent;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    SplashComponent getSplashComponent();
    SignInComponent getSignInComponent();
    SignUpComponent getSignUpComponent();
    HomeComponent getHomeComponent(HomeModule module);

}
