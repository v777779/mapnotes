package ru.vpcb.map.notes.di.activity.login.signup;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import ru.vpcb.map.notes.activity.login.signup.SignUpMvpPresenter;
import ru.vpcb.map.notes.activity.login.signup.SignUpPresenter;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.di.activity.ActivityScope;
import ru.vpcb.map.notes.executors.IAppExecutors;
import ru.vpcb.map.notes.manager.FAManager;

@Module
public class SignUpModule {
    private Activity activity;

    public SignUpModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    SignUpMvpPresenter provideSignUpMvpPresenter(IAppExecutors appExecutors, UserRepository userRepository){
        return new SignUpPresenter(appExecutors,userRepository);
    }

    @Provides
    @ActivityScope
    FAManager provideFAManager() {
        return new FAManager(activity);
    }

}
