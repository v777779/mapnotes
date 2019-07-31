package ru.vpcb.map.notes.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.vpcb.map.notes.data.repository.FirebaseUserRepository;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.executors.AppExecutor;
import ru.vpcb.map.notes.executors.IAppExecutors;

@Module
public class AppModule {

    @Provides
    @Singleton
    IAppExecutors provideAppExecutors() {
        return new AppExecutor();
    }

    @Provides
    @Singleton
    UserRepository provideFirebaseUserRepository(IAppExecutors appExecutors) {
        return new FirebaseUserRepository(appExecutors);
    }




}
