package ru.vpcb.map.notes.activity.splash;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import ru.vpcb.map.notes.MockTest;
import ru.vpcb.map.notes.di.activity.splash.SplashComponent;
import ru.vpcb.map.notes.di.activity.splash.SplashModule;

import static org.mockito.Mockito.when;
import static ru.vpcb.map.notes.robots.HomeScreenRobot.homeScreen;
import static ru.vpcb.map.notes.robots.LoginScreenRobot.loginScreen;
import static ru.vpcb.map.notes.robots.PreparationRobot.prepare;
import static ru.vpcb.map.notes.robots.SplashScreenRobot.splashActivityMockTestRule;
import static ru.vpcb.map.notes.robots.SplashScreenRobot.splashScreen;

@RunWith(AndroidJUnit4.class)
public class SplashActivityTest extends MockTest {

    @Rule
    public RuleChain chain = RuleChain.outerRule(permissionRule).around(splashActivityMockTestRule);

    @Mock
    private SplashComponent.Builder splashBuilder;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

// splash
        SplashComponent splashComponent = new SplashComponent() {
            @Override
            public void inject(SplashActivity activity) {
                activity.presenter = new SplashPresenter(appExecutors, userRepository);
            }
        };
        when(splashBuilder.module(Mockito.any(SplashModule.class))).thenReturn(splashBuilder);
        when(splashBuilder.build()).thenReturn(splashComponent);

        app.put(SplashActivity.class, splashBuilder);

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

