package ru.vpcb.test.map.di.activity.login.signin;

import dagger.Module;
import dagger.Provides;
import ru.vpcb.test.map.activity.login.signin.SignInMvpPresenter;
import ru.vpcb.test.map.activity.login.signin.SignInPresenter;
import ru.vpcb.test.map.data.repository.UserRepository;
import ru.vpcb.test.map.di.activity.ActivityScope;
import ru.vpcb.test.map.executors.IAppExecutors;

@Module
public class SignInModule {
    @Provides
    @ActivityScope
    SignInMvpPresenter provideSignInMvpPresenter(IAppExecutors appExecutors,
                                                 UserRepository userRepository) {
        return new SignInPresenter(appExecutors, userRepository);
    }
}
