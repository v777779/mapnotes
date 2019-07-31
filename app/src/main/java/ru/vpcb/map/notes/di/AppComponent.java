package ru.vpcb.map.notes.di;


import javax.inject.Singleton;

import dagger.Component;
import ru.vpcb.map.notes.di.activity.home.HomeComponent;
import ru.vpcb.map.notes.di.activity.home.HomeModule;
import ru.vpcb.map.notes.di.activity.login.signin.SignInComponent;
import ru.vpcb.map.notes.di.activity.login.signin.SignInModule;
import ru.vpcb.map.notes.di.activity.login.signup.SignUpComponent;
import ru.vpcb.map.notes.di.activity.login.signup.SignUpModule;
import ru.vpcb.map.notes.di.activity.splash.SplashComponent;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    SplashComponent getSplashComponent();
    SignInComponent getSignInComponent(SignInModule module);
    SignUpComponent getSignUpComponent(SignUpModule module);
    HomeComponent getHomeComponent(HomeModule module);

}
