package ru.vpcb.map.notes;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.vpcb.map.notes.activity.home.HomeActivity;
import ru.vpcb.map.notes.activity.home.HomeActivityAccess;
import ru.vpcb.map.notes.activity.home.HomeMvpPresenter;
import ru.vpcb.map.notes.activity.home.HomePresenter;
import ru.vpcb.map.notes.data.formatter.LocationFormatter;
import ru.vpcb.map.notes.data.provider.LocationProvider;
import ru.vpcb.map.notes.data.repository.NotesRepository;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.di.activity.home.HomeComponent;
import ru.vpcb.map.notes.di.activity.home.HomeModule;
import ru.vpcb.map.notes.executors.IAppExecutors;
import ru.vpcb.map.notes.fragments.add.AddNoteFragment;
import ru.vpcb.map.notes.fragments.add.AddNoteFragmentAccess;
import ru.vpcb.map.notes.fragments.add.AddNoteMvpPresenter;
import ru.vpcb.map.notes.fragments.add.AddNotePresenter;
import ru.vpcb.map.notes.fragments.map.GoogleMapFragment;
import ru.vpcb.map.notes.fragments.map.MapFragment;
import ru.vpcb.map.notes.fragments.search.SearchNotesFragment;
import ru.vpcb.map.notes.fragments.search.SearchNotesFragmentAccess;
import ru.vpcb.map.notes.fragments.search.SearchNotesMvpPresenter;
import ru.vpcb.map.notes.fragments.search.SearchNotesPresenter;
import ru.vpcb.map.notes.manager.FAManager;

import static org.mockito.Mockito.when;

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
    @Mock
    protected  HomeComponent.Builder homeBuilder;

    protected  MainApp app;
    private MapFragment mapFragment;
    protected MockTest testScope;

    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(appExecutors.ui()).thenReturn(AndroidSchedulers.mainThread());
        Mockito.when(appExecutors.net()).thenReturn(AndroidSchedulers.mainThread());

        mapFragment = new FakeMapFragment();
        testScope = this;

// home
        HomeComponent homeComponent = new HomeComponent() {
            @Override
            public void inject(HomeActivity activity) {
                HomeMvpPresenter presenter = new HomePresenter(appExecutors, userRepository);
                HomeActivityAccess.set(activity, presenter, mapFragment, analyticsManager);
            }

            @Override
            public void inject(GoogleMapFragment fragment) {

            }

            @Override
            public void inject(AddNoteFragment fragment) {
                AddNoteMvpPresenter presenter = new AddNotePresenter(userRepository,
                        notesRepository, locationProvider, locationFormatter);
                AddNoteFragmentAccess.set(fragment, presenter);

            }

            @Override
            public void inject(SearchNotesFragment fragment) {
                SearchNotesMvpPresenter presenter = new SearchNotesPresenter(appExecutors,
                        userRepository, notesRepository);
                SearchNotesFragmentAccess.set(fragment, presenter);
            }
        };

        when(homeBuilder.module(Mockito.any(HomeModule.class))).thenReturn(homeBuilder);
        when(homeBuilder.build()).thenReturn(homeComponent);
        app = ApplicationProvider.getApplicationContext();
        app.put(HomeActivity.class, homeBuilder);

        Intents.init();                     // espresso intents
    }

    public void tearDown() throws Exception {
        Intents.release();                  // espresso intents
        app.clearHomeComponent();
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
