package ru.vpcb.map.notes;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.vpcb.map.notes.data.formatter.LocationFormatter;
import ru.vpcb.map.notes.data.provider.LocationProvider;
import ru.vpcb.map.notes.data.repository.NotesRepository;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.executors.IAppExecutors;
import ru.vpcb.map.notes.fragments.map.MapFragment;
import ru.vpcb.map.notes.manager.FAManager;

public class MockTest {

    @Rule
    public GrantPermissionRule permissionRule =
            GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    @Mock
    protected UserRepository userRepository;
    @Mock
    protected NotesRepository notesRepository;
    @Mock
    protected IAppExecutors appExecutors;
    @Mock
    protected LocationProvider locationProvider;
    @Mock
    protected LocationFormatter locationFormatter;
    @Mock
    protected FAManager analyticsManager;

    protected MapFragment mapFragment;

    protected MockTest testScope;


    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(appExecutors.ui()).thenReturn(AndroidSchedulers.mainThread());
        Mockito.when(appExecutors.net()).thenReturn(AndroidSchedulers.mainThread());

        mapFragment = new FakeMapFragment();
        testScope = this;

        Intents.init();                     // espresso intents
    }

    public void tearDown() throws Exception {
        TestMainApp app = ApplicationProvider.getApplicationContext();
        app.clearAll();

        Intents.release();                  // espresso intents
    }

    public LocationProvider getLocationProvider() {
        return locationProvider;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public NotesRepository getNotesRepository() {
        return notesRepository;
    }

    public LocationFormatter getLocationFormatter() {
        return locationFormatter;
    }

    public MapFragment getMapFragment() {
        return mapFragment;
    }

    public GrantPermissionRule getPermissionRule() {
        return permissionRule;
    }
}
