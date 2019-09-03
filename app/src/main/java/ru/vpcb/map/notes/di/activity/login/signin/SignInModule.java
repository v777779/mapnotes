package ru.vpcb.map.notes.di.activity.login.signin;

import dagger.Module;
import dagger.Provides;
import ru.vpcb.map.notes.activity.login.signin.SignInActivity;
import ru.vpcb.map.notes.activity.login.signin.SignInMvpPresenter;
import ru.vpcb.map.notes.activity.login.signin.SignInPresenter;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.di.ActivityModule;
import ru.vpcb.map.notes.di.activity.ActivityScope;
import ru.vpcb.map.notes.executors.IAppExecutors;
import ru.vpcb.map.notes.manager.FAManager;

@Module
public class SignInModule extends ActivityModule<SignInActivity> {

    public SignInModule(SignInActivity activity) {
        super(activity);
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
