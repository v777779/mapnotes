package ru.vpcb.map.notes.di.activity.login.signin;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import ru.vpcb.map.notes.activity.login.signin.SignInMvpPresenter;
import ru.vpcb.map.notes.activity.login.signin.SignInPresenter;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.di.activity.ActivityScope;
import ru.vpcb.map.notes.executors.IAppExecutors;
import ru.vpcb.map.notes.manager.FAManager;

@Module
public class SignInModule {

    private Activity activity;

    public SignInModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    SignInMvpPresenter provideSignInMvpPresenter(IAppExecutors appExecutors,
                                                 UserRepository userRepository) {
        return new SignInPresenter(appExecutors, userRepository);
    }

    @Provides
    @ActivityScope
    FAManager provideFAManager() {
        return new FAManager(activity);
    }
}
