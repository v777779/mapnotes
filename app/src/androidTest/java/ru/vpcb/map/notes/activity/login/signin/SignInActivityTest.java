package ru.vpcb.map.notes.activity.login.signin;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import ru.vpcb.map.notes.MainApp;
import ru.vpcb.map.notes.MockTest;
import ru.vpcb.map.notes.activity.home.HomeActivity;
import ru.vpcb.map.notes.activity.home.HomeActivityAccess;
import ru.vpcb.map.notes.activity.home.HomeMvpPresenter;
import ru.vpcb.map.notes.activity.home.HomePresenter;
import ru.vpcb.map.notes.di.HomeAdapter;
import ru.vpcb.map.notes.di.activity.home.HomeComponent;
import ru.vpcb.map.notes.di.activity.home.HomeModule;
import ru.vpcb.map.notes.di.activity.login.signin.SignInComponent;
import ru.vpcb.map.notes.di.activity.login.signin.SignInModule;

import static org.mockito.Mockito.when;
import static ru.vpcb.map.notes.robots.HomeScreenRobot.homeScreen;
import static ru.vpcb.map.notes.robots.PreparationRobot.prepare;
import static ru.vpcb.map.notes.robots.SignInScreenRobot.signInActivity;
import static ru.vpcb.map.notes.robots.SignInScreenRobot.signInScreen;

@RunWith(AndroidJUnit4.class)
public class SignInActivityTest extends MockTest {

    @Rule
    public RuleChain chain = RuleChain.outerRule(permissionRule).around(signInActivity);  // runs in test method

    @Mock
    private SignInComponent.Builder signInBuilder;
    @Mock
    private HomeComponent.Builder homeBuilder;

    private String emptyEmail;
    private String correctEmail;
    private String incorrectEmail;
    private String password;
    private String emptyPassword;


    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        emptyEmail = "";
        correctEmail = "test@test.com";
        incorrectEmail = "test";
        password = "password";
        emptyPassword = "";
// sign in
        SignInComponent signInComponent = new SignInComponent() {
            @Override
            public void inject(SignInActivity activity) {
                activity.presenter = new SignInPresenter(appExecutors, userRepository);
                activity.analyticsManager = analyticsManager;
            }
        };

        when(signInBuilder.module(Mockito.any(SignInModule.class))).thenReturn(signInBuilder);
        when(signInBuilder.build()).thenReturn(signInComponent);

// home
        HomeComponent homeComponent = new HomeAdapter() {
            @Override
            public void inject(HomeActivity activity) {
                HomeMvpPresenter presenter = new HomePresenter(appExecutors, userRepository);
                HomeActivityAccess.set(activity, presenter, mapFragment, analyticsManager);
            }
        };
        when(homeBuilder.module(Mockito.any(HomeModule.class))).thenReturn(homeBuilder);
        when(homeBuilder.build()).thenReturn(homeComponent);

        MainApp app = ApplicationProvider.getApplicationContext();
        app.put(SignInActivity.class, signInBuilder);
        app.put(HomeActivity.class, homeBuilder);

    }

    @Test
    public void whenEmailIsEmptyShouldDisplayEmailError() {
        signInScreen()
                .displayAsEntryPoint()
                .signIn(emptyEmail, emptyPassword)
                .isIncorrectEmailErrorDisplayed();

    }

    @Test
    public void whenEmailIsNotCorrectShouldDisplayEmailError() {
        signInScreen()
                .displayAsEntryPoint()
                .signIn(incorrectEmail, password)
                .isIncorrectEmailErrorDisplayed();

    }

    @Test
    public void whenPasswordIsEmptyShouldDisplayPasswordError() {
        signInScreen()
                .displayAsEntryPoint()
                .signIn(correctEmail, emptyPassword)
                .isEmptyPasswordErrorDisplayed();

    }

    @Test
    public void whenSignInErrorShouldDisplaySignInError() {
        prepare(testScope)
                .mockSignInErrorWithException();

        signInScreen()
                .displayAsEntryPoint()
                .signIn(correctEmail, password)
                .isSignInErrorDisplayed();
    }

    @Test
    public void whenSignInSuccessShouldOpenMapScreen() {
        prepare(testScope)
                .mockLocationProvider()
                .mockSignInSuccess(correctEmail, password);

        signInScreen()
                .displayAsEntryPoint()
                .signIn(correctEmail, password);

        homeScreen().
                isSuccessfullyLoaded();

    }

    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();


    }


}
