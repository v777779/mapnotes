package ru.vpcb.map.notes.di.activity.login.signup;

import dagger.Module;
import dagger.Provides;
import ru.vpcb.map.notes.activity.login.signup.SignUpMvpPresenter;
import ru.vpcb.map.notes.activity.login.signup.SignUpPresenter;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.di.activity.ActivityScope;
import ru.vpcb.map.notes.executors.IAppExecutors;

@Module
public class SignUpModule {

    @Provides
    @ActivityScope
    SignUpMvpPresenter provideSignUpMvpPresenter(IAppExecutors appExecutors, UserRepository userRepository){
        return new SignUpPresenter(appExecutors,userRepository);
    }
}
