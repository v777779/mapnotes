package ru.vpcb.map.notes.di;


import javax.inject.Singleton;

import dagger.Component;
import ru.vpcb.map.notes.MainApp;

@Singleton
@Component(modules = {AppModule.class, BindingModule.class})
public interface AppComponent {

//    SplashComponent getSplashComponent();

//    SignInComponent getSignInComponent(SignInModule module);
//
//    SignUpComponent getSignUpComponent(SignUpModule module);

//    HomeComponent getHomeComponent(HomeModule module);

    void inject(MainApp app);

//    Map<Class<? extends Activity>, Provider<ActivityComponentBuilder>> get();  // проверка
}
