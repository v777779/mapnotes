package ru.vpcb.map.notes.di.activity.login.signin;

import dagger.Module;
import dagger.Provides;
import ru.vpcb.map.notes.activity.login.signin.SignInMvpPresenter;
import ru.vpcb.map.notes.activity.login.signin.SignInPresenter;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.di.activity.ActivityScope;
import ru.vpcb.map.notes.executors.IAppExecutors;

@Module
public class SignInModule {
    @Provides
    @ActivityScope
    SignInMvpPresenter provideSignInMvpPresenter(IAppExecutors appExecutors,
                                                 UserRepository userRepository) {
        return new SignInPresenter(appExecutors, userRepository);
    }
}
