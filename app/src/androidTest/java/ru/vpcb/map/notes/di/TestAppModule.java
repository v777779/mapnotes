package ru.vpcb.map.notes.di;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.vpcb.map.notes.data.Result;
import ru.vpcb.map.notes.data.repository.FirebaseNotesRepository;
import ru.vpcb.map.notes.data.repository.FirebaseUserRepository;
import ru.vpcb.map.notes.data.repository.NotesRepository;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.executors.AppExecutors;
import ru.vpcb.map.notes.executors.IAppExecutors;
import ru.vpcb.map.notes.model.AuthUser;

public class TestAppModule extends AppModule {

    private UserRepository userRepository;
    private NotesRepository notesRepository;
    private IAppExecutors appExecutors;

    public TestAppModule(IAppExecutors appExecutors, UserRepository userRepository,
                         NotesRepository notesRepository) {
        this.appExecutors = appExecutors;
        this.userRepository = userRepository;
        this.notesRepository = notesRepository;
    }

    @Override
    IAppExecutors provideAppExecutors() {
        return appExecutors;
    }

    @Override
    UserRepository provideFirebaseUserRepository(IAppExecutors appExecutors) {
        return userRepository;
    }

    @Override
    NotesRepository provideFirebaseNotesRepository(IAppExecutors appExecutors) {
        return notesRepository;
    }


}
