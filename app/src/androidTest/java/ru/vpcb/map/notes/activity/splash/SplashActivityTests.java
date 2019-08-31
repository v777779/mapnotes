package ru.vpcb.map.notes.activity.splash;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ru.vpcb.map.notes.IModuleSupplier;
import ru.vpcb.map.notes.MockTest;
import ru.vpcb.map.notes.TestMainApp;
import ru.vpcb.map.notes.activity.home.HomeActivity;
import ru.vpcb.map.notes.di.TestAppModule;
import ru.vpcb.map.notes.di.activity.home.HomeModule;
import ru.vpcb.map.notes.di.activity.home.TestHomeModule;

import static ru.vpcb.map.notes.robots.HomeScreenRobot.homeScreen;
import static ru.vpcb.map.notes.robots.LoginScreenRobot.loginScreen;
import static ru.vpcb.map.notes.robots.PreparationRobot.prepare;
import static ru.vpcb.map.notes.robots.SplashScreenRobot.splashScreen;


public class SplashActivityTests extends MockTest {

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        IModuleSupplier supplier = new IModuleSupplier() {
            @Override
            public HomeModule apply(HomeActivity activity) {
                return new TestHomeModule(activity, locationProvider, locationFormatter,
                        mapFragment, analyticsManager);
            }

            @Override
            public TestAppModule apply() {
                return new TestAppModule(appExecutors, userRepository, notesRepository);
            }
        };

        TestMainApp app = ApplicationProvider.getApplicationContext();
        app.setSupplier(supplier);

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

