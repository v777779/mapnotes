package ru.vpcb.map.notes.activity.splash;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import ru.vpcb.map.notes.MainApp;
import ru.vpcb.map.notes.MockTest;
import ru.vpcb.map.notes.activity.home.HomeActivity;
import ru.vpcb.map.notes.activity.home.HomeActivityAccess;
import ru.vpcb.map.notes.activity.home.HomeMvpPresenter;
import ru.vpcb.map.notes.activity.home.HomePresenter;
import ru.vpcb.map.notes.di.activity.home.HomeComponent;
import ru.vpcb.map.notes.di.activity.home.HomeModule;
import ru.vpcb.map.notes.di.activity.splash.SplashComponent;
import ru.vpcb.map.notes.di.activity.splash.SplashModule;
import ru.vpcb.map.notes.fragments.add.AddNoteFragment;
import ru.vpcb.map.notes.fragments.map.GoogleMapFragment;
import ru.vpcb.map.notes.fragments.search.SearchNotesFragment;

import static org.mockito.Mockito.when;
import static ru.vpcb.map.notes.robots.HomeScreenRobot.homeScreen;
import static ru.vpcb.map.notes.robots.LoginScreenRobot.loginScreen;
import static ru.vpcb.map.notes.robots.PreparationRobot.prepare;
import static ru.vpcb.map.notes.robots.SplashScreenRobot.splashScreen;


public class SplashActivityTests extends MockTest {

    private SplashComponent splashComponent;
    private HomeComponent homeComponent;

    @Mock
    private SplashComponent.Builder splashBuilder;
    @Mock
    private HomeComponent.Builder homeBuilder;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

// component
        splashComponent= new SplashComponent() {
            @Override
            public void inject(SplashActivity activity) {
                activity.presenter = new SplashPresenter(appExecutors,userRepository);
            }
        };
        when(splashBuilder.module(Mockito.any(SplashModule.class))).thenReturn(splashBuilder);
        when(splashBuilder.build()).thenReturn(splashComponent);
        MainApp app = ApplicationProvider.getApplicationContext();
        app.put(SplashActivity.class, splashBuilder);

// component
        homeComponent= new HomeComponent(){
            @Override
            public void inject(HomeActivity activity) {
            HomeMvpPresenter presenter = new HomePresenter(appExecutors,userRepository);
                HomeActivityAccess.set(activity, presenter,mapFragment,analyticsManager);
            }

            @Override
            public void inject(GoogleMapFragment fragment) {
//                @Inject
//                MapMvpPresenter presenter;
//                @Inject
//                LocationProvider locationProvider;
//                @Inject
//                FAManager analyticsManager;
//                @Inject
//                Activity activity;

            }

            @Override
            public void inject(SearchNotesFragment fragment) {
//                @Inject
//                SearchNotesMvpPresenter presenter;
//                @Inject
//                Activity activity;
            }

            @Override
            public void inject(AddNoteFragment fragment) {

//                @Inject
//                AddNoteMvpPresenter presenter;
//                @Inject
//                Activity activity;
            }

        };
        when(homeBuilder.module(Mockito.any(HomeModule.class))).thenReturn(homeBuilder);
        when(homeBuilder.build()).thenReturn(homeComponent);
        app.put(HomeActivity.class, homeBuilder);

    }

    @Test
    public void whenUserIsAuthenticatedShouldOpenHomeActivity() {
        prepare(testScope)
                .mockLocationProvider()
                .mockAuthorizedUser();
        splashScreen()
                .displayMockAsEntryPoint();
        homeScreen()
                .isSuccessfullyLoaded();
    }

    @Test
    public void whenUserIsNotAuthenticatedShouldOpenHomeActivity() {
        prepare(testScope)
                .mockNoAuthorizedUser();
        splashScreen()
                .displayMockAsEntryPoint();
        loginScreen()
                .isSuccessfullyLoaded();
    }

    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();

    }
}

