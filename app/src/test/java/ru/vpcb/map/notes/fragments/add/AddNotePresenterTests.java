package ru.vpcb.map.notes.fragments.add;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.vpcb.map.notes.MainApp;
import ru.vpcb.map.notes.data.Result;
import ru.vpcb.map.notes.data.formatter.LocationFormatter;
import ru.vpcb.map.notes.data.provider.LocationProvider;
import ru.vpcb.map.notes.data.repository.NotesRepository;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.executors.IAppExecutors;
import ru.vpcb.map.notes.executors.IConsumer;
import ru.vpcb.map.notes.model.AuthUser;
import ru.vpcb.map.notes.model.Location;
import ru.vpcb.map.notes.model.Note;

@RunWith(RobolectricTestRunner.class)   // setup Robolectric  build.gradle, gradle.properties
@Config(
        sdk = 28,
        application = MainApp.class
)
public class AddNotePresenterTests {
    private String testNoteText;
    private Result.Success<AuthUser> authUser;
    private Result.Error<AuthUser> notAuthUser;

    private IConsumer<Location> listener;
    private Location currentLocation;
    private String currentLocationString;

    @Mock
    private AddNoteView view;
    @Mock
    private IAppExecutors appExecutors;
    @Mock
    private UserRepository userRepository;
    @Mock
    private NotesRepository notesRepository;
    @Mock
    private LocationProvider locationProvider;
    @Mock
    private LocationFormatter locationFormatter;

    @Captor
    ArgumentCaptor<IConsumer<Location>> captor;

    private AddNoteMvpPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        String authUserUID = "111111";
        testNoteText = "test note";
        authUser = new Result.Success<>(new AuthUser(authUserUID));
        notAuthUser = new Result.Error<>(new RuntimeException("Auth Error"));

        currentLocation = new Location(-33.8688, 151.2093);
        currentLocationString = "-33.8688, 151.2093";

        presenter = new AddNotePresenter(userRepository, notesRepository, locationProvider,
                locationFormatter);

        Mockito.when(appExecutors.ui()).then(
                (Answer<Scheduler>) invocation -> AndroidSchedulers.mainThread());
        Mockito.when(appExecutors.net()).then(
                (Answer<Scheduler>) invocation -> AndroidSchedulers.mainThread());

        Mockito.doAnswer(invocation -> null).when(view).clearNoteText();
        Mockito.doAnswer(invocation -> null).when(view).hideKeyboard();
        Mockito.doAnswer(invocation -> null).when(locationProvider).startLocationUpdates();
        Mockito.doAnswer(invocation -> null).when(locationProvider).stopLocationUpdates();
        Mockito.doNothing().when(locationProvider)
                .addUpdatableLocationListener(ArgumentMatchers.any());

        Mockito.doAnswer(invocation -> null).when(notesRepository).addNote(Mockito.any());
        Mockito.when(userRepository.getCurrentUser()).thenReturn(authUser);

        Mockito.when(locationFormatter.format(currentLocation)).thenReturn(currentLocationString);

    }

// 0    onAttach

    @Test
    public void onAttachWithNonNullViewStartLocationUpdatesCalled() {
        presenter.onAttach(view);

        Mockito.verify(locationProvider, Mockito.times(1))
                .startLocationUpdates();
    }

    @Test
    public void onAttachWithNullViewStartLocationUpdatesNotCalled() {
        presenter.onAttach(null);

        Mockito.verify(locationProvider, Mockito.times(0))
                .startLocationUpdates();
    }

// 1    getCurrentLocation

    @Test
    public void getCurrentLocationWithNonNullViewDisplayCurrentLocationCalled() {
// capture arguments support
//        Mockito.doNothing().when(locationProvider).addUpdatableLocationListener(ArgumentMatchers.any());
//        Mockito.when(locationFormatter.format(currentLocation)).thenReturn(currentLocationString);

        presenter.onAttach(view);
        presenter.getCurrentLocation();
// capture arguments
        Mockito.verify(locationProvider).addUpdatableLocationListener(captor.capture());
        listener = captor.getValue();
        listener.accept(currentLocation);

        Mockito.verify(locationProvider, Mockito.times(1))
                .addUpdatableLocationListener(Mockito.any());
        Mockito.verify(locationFormatter, Mockito.times(1))
                .format(currentLocation);
        Mockito.verify(view, Mockito.times(1))
                .displayCurrentLocation(currentLocationString);

        Assert.assertEquals(currentLocation,presenter.getLastLocation());
    }

    @Test
    public void getCurrentLocationWithNullViewDisplayCurrentLocationNotCalled() {
        presenter.onAttach(null);
        presenter.getCurrentLocation();

        Mockito.verify(locationProvider).addUpdatableLocationListener(captor.capture());
        listener = captor.getValue();
        listener.accept(currentLocation);

        Mockito.verify(locationProvider, Mockito.times(1))
                .addUpdatableLocationListener(Mockito.any());
        Mockito.verify(locationFormatter, Mockito.times(0))
                .format(currentLocation);
        Mockito.verify(view, Mockito.times(0))
                .displayCurrentLocation(currentLocationString);

        Assert.assertEquals(currentLocation,presenter.getLastLocation());
    }

    @Test
    public void getCurrentLocationWithViewDetachedDisplayCurrentLocationCalled() {
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.getCurrentLocation();

        Mockito.verify(locationProvider).addUpdatableLocationListener(captor.capture());
        listener = captor.getValue();
        listener.accept(currentLocation);

        Mockito.verify(locationProvider, Mockito.times(1))
                .addUpdatableLocationListener(Mockito.any());
        Mockito.verify(locationFormatter, Mockito.times(0))
                .format(currentLocation);
        Mockito.verify(view, Mockito.times(0))
                .displayCurrentLocation(currentLocationString);

        Assert.assertEquals(currentLocation,presenter.getLastLocation());
    }

// 2    addNote     user authenticated

    @Test
    public void addNoteUserAuthenticatedWithNonNullViewAddNoteCalled() {
        presenter.onAttach(view);
        presenter.addNote(testNoteText);

        Mockito.verify(view, Mockito.times(1)).clearNoteText();
        Mockito.verify(view, Mockito.times(1)).hideKeyboard();
        Mockito.verify(notesRepository, Mockito.times(1))
                .addNote(Mockito.any(Note.class));
        Mockito.verify(view, Mockito.times(1))
                .displayCurrentLocation(Mockito.any(Note.class));
    }

    @Test
    public void addNoteUserAuthenticatedWithNullViewAddNoteNotCalled() {
        presenter.onAttach(null);
        presenter.addNote(testNoteText);

        Mockito.verify(view, Mockito.times(0)).clearNoteText();
        Mockito.verify(view, Mockito.times(0)).hideKeyboard();
        Mockito.verify(notesRepository, Mockito.times(0))
                .addNote(Mockito.any(Note.class));
        Mockito.verify(view, Mockito.times(0))
                .displayCurrentLocation(Mockito.any(Note.class));
    }

    @Test
    public void addNoteUserAuthenticatedWithViewDetachedAddNoteNotCalled() {
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.addNote(testNoteText);

        Mockito.verify(view, Mockito.times(0)).clearNoteText();
        Mockito.verify(view, Mockito.times(0)).hideKeyboard();
        Mockito.verify(notesRepository, Mockito.times(0))
                .addNote(Mockito.any(Note.class));
        Mockito.verify(view, Mockito.times(0))
                .displayCurrentLocation(Mockito.any(Note.class));
    }

// 3    addNote     user not authenticated

    @Test
    public void addNoteUserNotAuthenticatedWithNonNullViewAddNoteNotCalled() {
        Mockito.when(userRepository.getCurrentUser()).thenReturn(notAuthUser);

        presenter.onAttach(view);
        presenter.addNote(testNoteText);

        Mockito.verify(view, Mockito.times(1)).clearNoteText();
        Mockito.verify(view, Mockito.times(1)).hideKeyboard();
        Mockito.verify(notesRepository, Mockito.times(0))
                .addNote(Mockito.any(Note.class));
        Mockito.verify(view, Mockito.times(0))
                .displayCurrentLocation(Mockito.any(Note.class));
    }

    @Test
    public void addNoteUserNotAuthenticatedWithNullViewAddNoteNotCalled() {
        Mockito.when(userRepository.getCurrentUser()).thenReturn(notAuthUser);

        presenter.onAttach(null);
        presenter.addNote(testNoteText);

        Mockito.verify(view, Mockito.times(0)).clearNoteText();
        Mockito.verify(view, Mockito.times(0)).hideKeyboard();
        Mockito.verify(notesRepository, Mockito.times(0))
                .addNote(Mockito.any(Note.class));
        Mockito.verify(view, Mockito.times(0))
                .displayCurrentLocation(Mockito.any(Note.class));
    }

    @Test
    public void addNoteUserNotAuthenticatedWithViewDetachedAddNoteNotCalled() {
        Mockito.when(userRepository.getCurrentUser()).thenReturn(notAuthUser);

        presenter.onAttach(view);
        presenter.onDetach();
        presenter.addNote(testNoteText);

        Mockito.verify(view, Mockito.times(0)).clearNoteText();
        Mockito.verify(view, Mockito.times(0)).hideKeyboard();
        Mockito.verify(notesRepository, Mockito.times(0))
                .addNote(Mockito.any(Note.class));
        Mockito.verify(view, Mockito.times(0))
                .displayCurrentLocation(Mockito.any(Note.class));
    }

// 4    onDetach

    @Test
    public void onDetachWithNonNullViewStartLocationUpdatesCalled() {
        presenter.onAttach(view);
        presenter.onDetach();

        Mockito.verify(locationProvider, Mockito.times(1))
                .stopLocationUpdates();
    }

    @Test
    public void onDettachWithNullViewStartLocationUpdatesNotCalled() {
        presenter.onAttach(null);
        presenter.onDetach();

        Mockito.verify(locationProvider, Mockito.times(0))
                .stopLocationUpdates();
    }

    @After
    public void tearDown() throws Exception {

    }
}
