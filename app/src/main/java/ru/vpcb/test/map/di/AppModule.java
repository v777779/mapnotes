package ru.vpcb.test.map.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.vpcb.test.map.executors.AppExecutor;
import ru.vpcb.test.map.executors.IAppExecutors;

@Module
public class AppModule {

    @Provides
    @Singleton
    IAppExecutors provideAppExecutors() {
        return new AppExecutor();
    }


}
