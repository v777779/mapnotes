package ru.vpcb.map.notes.di;

import android.app.Activity;

import dagger.Binds;
import dagger.MapKey;
import dagger.Module;
import dagger.multibindings.IntoMap;
import ru.vpcb.map.notes.activity.home.HomeActivity;
import ru.vpcb.map.notes.activity.login.signin.SignInActivity;
import ru.vpcb.map.notes.activity.login.signup.SignUpActivity;
import ru.vpcb.map.notes.activity.splash.SplashActivity;
import ru.vpcb.map.notes.di.activity.home.HomeComponent;
import ru.vpcb.map.notes.di.activity.login.signin.SignInComponent;
import ru.vpcb.map.notes.di.activity.login.signup.SignUpComponent;
import ru.vpcb.map.notes.di.activity.splash.SplashComponent;

@Module(subcomponents = {SplashComponent.class, HomeComponent.class, SignInComponent.class,
        SignUpComponent.class})
public abstract class BindingModule {

    @MapKey
    public @interface ActivityKey {
        Class<? extends Activity> value();
    }

    @Binds
    @IntoMap
    @ActivityKey(SplashActivity.class)
    public abstract ActivityComponentBuilder provideSplashComponentBuilder(
            SplashComponent.Builder builder);

    @Binds
    @IntoMap
    @ActivityKey(HomeActivity.class)
    public abstract ActivityComponentBuilder provideHomeComponentBuilder(
            HomeComponent.Builder builder);

    @Binds
    @IntoMap
    @ActivityKey(SignInActivity.class)
    public abstract ActivityComponentBuilder provideSignInComponentBuilder(
            SignInComponent.Builder builder);

    @Binds
    @IntoMap
    @ActivityKey(SignUpActivity.class)
    public abstract ActivityComponentBuilder provideSignUpComponentBuilder(
            SignUpComponent.Builder builder);

}
