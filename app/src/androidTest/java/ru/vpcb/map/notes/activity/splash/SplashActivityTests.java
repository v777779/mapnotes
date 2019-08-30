package ru.vpcb.map.notes.activity.splash;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import ru.vpcb.map.notes.MockTest;
import ru.vpcb.map.notes.di.AppComponent;

import static ru.vpcb.map.notes.robots.HomeScreenRobot.homeScreen;
import static ru.vpcb.map.notes.robots.LoginScreenRobot.loginScreen;
import static ru.vpcb.map.notes.robots.PreparationRobot.prepare;
import static ru.vpcb.map.notes.robots.SplashScreenRobot.splashActivityMockTestRule;
import static ru.vpcb.map.notes.robots.SplashScreenRobot.splashScreen;


public class SplashActivityTests extends MockTest {


    private AppComponent testAppComponent;


    @Rule
    public ActivityTestRule<SplashActivity> activityRule = splashActivityMockTestRule;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);


    }

    @Test
    public void whenUserIsAuthenticatedShouldOpenHomeActivity() {
// TODO Mock Dagger Component with MainApp or Activity Component

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
// TODO Mock Dagger Component with MainApp or Activity Component

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

// alternative
//        testAppComponent = DaggerAppComponent
//                .builder()
//                .appModule(new TModule())
//                .build();
//
//        MainApp app = ApplicationProvider.getApplicationContext();
//        app.setComponent(testAppComponent);
//public class HModule extends HomeModule {
//    private  Activity activity;
//    public HModule(Activity activity) {
//        super(activity);
//        this.activity = activity;
//    }
//
//    @Override
//    MapFragment provideMapFragment() {
//        return Mockito.mock(MapFragment.class);
//    }
//
//    @Override
//    LocationProvider provideAddressLocationProvider() {
//        return Mockito.mock(LocationProvider.class);
//    }
//
//    @Override
//    LocationFormatter provideLocationFormatter(Activity activity) {
//        return Mockito.mock(LocationFormatter.class);
//    }
//
//
//}
