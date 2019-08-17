package ru.vpcb.map.notes.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.vpcb.map.notes.data.repository.FirebaseNotesRepository;
import ru.vpcb.map.notes.data.repository.FirebaseUserRepository;
import ru.vpcb.map.notes.data.repository.NotesRepository;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.executors.AppExecutors;
import ru.vpcb.map.notes.executors.IAppExecutors;

@Module
public class AppModule {

    @Provides
    @Singleton
    IAppExecutors provideAppExecutors() {
        return new AppExecutors();
    }

    @Provides
    @Singleton
    UserRepository provideFirebaseUserRepository(IAppExecutors appExecutors) {
        return new FirebaseUserRepository(appExecutors);
    }

    @Provides
    @Singleton
    NotesRepository provideFirebaseNotesRepository(IAppExecutors appExecutors) {
        return new FirebaseNotesRepository(appExecutors);
    }



}
