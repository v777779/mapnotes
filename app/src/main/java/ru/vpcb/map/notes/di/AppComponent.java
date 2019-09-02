package ru.vpcb.map.notes.di;


import javax.inject.Singleton;

import dagger.Component;
import ru.vpcb.map.notes.MainApp;
import ru.vpcb.map.notes.di.activity.login.signin.SignInComponent;
import ru.vpcb.map.notes.di.activity.login.signin.SignInModule;
import ru.vpcb.map.notes.di.activity.login.signup.SignUpComponent;
import ru.vpcb.map.notes.di.activity.login.signup.SignUpModule;

@Singleton
@Component(modules = {AppModule.class, BindingModule.class})
public interface AppComponent {

//    SplashComponent getSplashComponent();

    SignInComponent getSignInComponent(SignInModule module);

    SignUpComponent getSignUpComponent(SignUpModule module);

//    HomeComponent getHomeComponent(HomeModule module);

    void inject(MainApp app);

//    Map<Class<? extends Activity>, Provider<ActivityComponentBuilder>> get();  // проверка
}
