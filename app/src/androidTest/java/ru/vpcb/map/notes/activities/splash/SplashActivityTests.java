package ru.vpcb.map.notes.activities.splash;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import ru.vpcb.map.notes.MockTest;
import ru.vpcb.map.notes.activity.splash.SplashActivity;
import ru.vpcb.map.notes.robots.PreparationRobot;

import static ru.vpcb.map.notes.robots.HomeScreenRobot.homeScreen;
import static ru.vpcb.map.notes.robots.PreparationRobot.prepare;
import static ru.vpcb.map.notes.robots.SplashScreenRobot.splashActivityMockTestRule;
import static ru.vpcb.map.notes.robots.SplashScreenRobot.splashScreen;


public class SplashActivityTests extends MockTest {

    @Rule
    ActivityTestRule<SplashActivity> activityRule = splashActivityMockTestRule;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void whenUserIsAuthenticatedShouldOpenHomeActivity() {

        PreparationRobot prepare = prepare(this);
        prepare.mockLocationProvider();
        prepare.mockAuthorizedUser();

        splashScreen().displayMockAsEntryPoint();

        homeScreen().isSuccessfullyLoaded();


    }

    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
