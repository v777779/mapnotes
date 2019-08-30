package ru.vpcb.map.notes.di;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.vpcb.map.notes.FakeMapFragment;
import ru.vpcb.map.notes.data.formatter.LocationFormatter;
import ru.vpcb.map.notes.data.provider.LocationProvider;
import ru.vpcb.map.notes.data.repository.NotesRepository;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.executors.IAppExecutors;
import ru.vpcb.map.notes.fragments.map.MapFragment;

@Module
public class TestMockAppModule {

    @Provides
    @Singleton
    IAppExecutors provideMockAppExecutors() {
        IAppExecutors appExecutors = Mockito.mock(IAppExecutors.class);
        Mockito.when(appExecutors.ui()).thenReturn(AndroidSchedulers.mainThread());
        Mockito.when(appExecutors.net()).thenReturn(AndroidSchedulers.mainThread());
        return appExecutors;
    }

    @Provides
    @Singleton
    UserRepository provideMockUserRepository() {
        return Mockito.mock(UserRepository.class);
    }

    @Provides
    @Singleton
    NotesRepository provideMockNotesRepository() {
        return Mockito.mock(NotesRepository.class);
    }

    @Provides
    @Singleton
    LocationProvider provideMockLocationProvider() {
        return Mockito.mock(LocationProvider.class);
    }

    @Provides
    @Singleton
    LocationFormatter provideMockLocationFormatter() {
        return Mockito.mock(LocationFormatter.class);
    }

    @Provides
    @Singleton
    MapFragment provideMockMapFragment() {
        return new FakeMapFragment();
    }





}
