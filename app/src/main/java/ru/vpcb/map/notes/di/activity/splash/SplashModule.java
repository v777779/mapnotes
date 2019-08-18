package ru.vpcb.map.notes.di.activity.splash;

import dagger.Module;
import dagger.Provides;
import ru.vpcb.map.notes.activity.splash.SplashMvpPresenter;
import ru.vpcb.map.notes.activity.splash.SplashPresenter;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.di.activity.ActivityScope;
import ru.vpcb.map.notes.executors.IAppExecutors;

@Module
public class SplashModule {

    @Provides
    @ActivityScope
    SplashMvpPresenter provideSplashMvpPresenter(IAppExecutors appExecutors,
                                                                   UserRepository userRepository) {
        return new SplashPresenter(appExecutors, userRepository);
    }

}
