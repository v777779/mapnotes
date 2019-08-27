package ru.vpcb.map.notes;

import androidx.test.espresso.intent.Intents;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;

import javax.inject.Inject;

import ru.vpcb.map.notes.data.provider.LocationProvider;
import ru.vpcb.map.notes.data.repository.NotesRepository;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.di.DaggerTestAppComponent;
import ru.vpcb.map.notes.di.TestAppComponent;
import ru.vpcb.map.notes.fragments.map.MapFragment;

public class MockTest {

    public static MockTest testScope() {
        return new MockTest();
    }

    @Rule
    public GrantPermissionRule permissionRule =
            GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);            ;

    @Inject
    protected LocationProvider locationProvider;
    @Inject
    protected UserRepository userRepository;
    @Inject
    protected NotesRepository notesRepository;
    @Inject
    protected MapFragment mapFragment;

    protected MockTest testScope;

    protected MockTest() {

    }


    public void setUp() throws Exception {
// open injects
        TestAppComponent component = DaggerTestAppComponent.builder().build();
        component.inject(this);
        Intents.init();     // espresso intents


        testScope = this;
    }


    public void tearDown() throws Exception {
// close injects if necessary here
        Intents.release();  // espresso intents
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

    public MapFragment getMapFragment() {
        return mapFragment;
    }

    public GrantPermissionRule getPermissionRule() {
        return permissionRule;
    }
}
