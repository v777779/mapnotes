package ru.vpcb.map.notes.activity.splash;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ru.vpcb.map.notes.MockTest;
import ru.vpcb.map.notes.TestMainApp;
import ru.vpcb.map.notes.data.Result;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.di.AppComponent;
import ru.vpcb.map.notes.di.DaggerAppComponent;
import ru.vpcb.map.notes.di.TestAppModule;
import ru.vpcb.map.notes.model.AuthUser;

import static ru.vpcb.map.notes.robots.HomeScreenRobot.homeScreen;
import static ru.vpcb.map.notes.robots.LoginScreenRobot.loginScreen;
import static ru.vpcb.map.notes.robots.PreparationRobot.prepare;
import static ru.vpcb.map.notes.robots.SplashScreenRobot.splashActivityMockTestRule;
import static ru.vpcb.map.notes.robots.SplashScreenRobot.splashScreen;


public class SplashActivityTests extends MockTest {

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        AppComponent component = DaggerAppComponent.builder()
                .appModule(new TestAppModule(userRepository, notesRepository))
                .build();
        TestMainApp app = ApplicationProvider.getApplicationContext();
        app.setComponent(component);

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

