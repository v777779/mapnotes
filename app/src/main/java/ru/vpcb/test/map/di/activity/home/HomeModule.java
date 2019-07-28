package ru.vpcb.test.map.di.activity.home;

import dagger.Module;
import dagger.Provides;
import ru.vpcb.test.map.activity.home.HomeMvpPresenter;
import ru.vpcb.test.map.activity.home.HomePresenter;
import ru.vpcb.test.map.data.repository.UserRepository;
import ru.vpcb.test.map.di.activity.ActivityScope;
import ru.vpcb.test.map.executors.IAppExecutors;

@Module
public class HomeModule {

    @ActivityScope
    @Provides
    HomeMvpPresenter provideHomeMvpPresenter(IAppExecutors appExecutors, UserRepository userRepository){
        return new HomePresenter(appExecutors,userRepository);
    }

}
