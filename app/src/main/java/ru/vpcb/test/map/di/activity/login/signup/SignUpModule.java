package ru.vpcb.test.map.di.activity.login.signup;

import dagger.Module;
import dagger.Provides;
import ru.vpcb.test.map.activity.login.signup.SignUpMvpPresenter;
import ru.vpcb.test.map.activity.login.signup.SignUpPresenter;
import ru.vpcb.test.map.data.repository.UserRepository;
import ru.vpcb.test.map.di.activity.ActivityScope;
import ru.vpcb.test.map.executors.IAppExecutors;

@Module
public class SignUpModule {

    @Provides
    @ActivityScope
    SignUpMvpPresenter provideSignUpMvpPresenter(IAppExecutors appExecutors, UserRepository userRepository){
        return new SignUpPresenter(appExecutors,userRepository);
    }
}
