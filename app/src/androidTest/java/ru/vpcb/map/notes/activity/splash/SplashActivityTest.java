package ru.vpcb.map.notes.activity.splash;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.vpcb.map.notes.MockTest;

import static ru.vpcb.map.notes.robots.HomeScreenRobot.homeScreen;
import static ru.vpcb.map.notes.robots.LoginScreenRobot.loginScreen;
import static ru.vpcb.map.notes.robots.PreparationRobot.prepare;
import static ru.vpcb.map.notes.robots.SplashScreenRobot.splashScreen;

@RunWith(AndroidJUnit4.class)
public class SplashActivityTest extends MockTest {

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

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

