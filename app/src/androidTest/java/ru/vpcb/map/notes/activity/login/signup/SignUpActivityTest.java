package ru.vpcb.map.notes.activity.login.signup;

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
import ru.vpcb.map.notes.di.activity.login.signup.SignUpComponent;
import ru.vpcb.map.notes.di.activity.login.signup.SignUpModule;

import static org.mockito.Mockito.when;
import static ru.vpcb.map.notes.robots.HomeScreenRobot.homeScreen;
import static ru.vpcb.map.notes.robots.PreparationRobot.prepare;
import static ru.vpcb.map.notes.robots.SignUpScreenRobot.signUpActivity;
import static ru.vpcb.map.notes.robots.SignUpScreenRobot.signUpScreen;

@RunWith(AndroidJUnit4.class)
public class SignUpActivityTest extends MockTest {

    @Rule
    public RuleChain chain = RuleChain.outerRule(permissionRule).around(signUpActivity);

    @Mock
    SignUpComponent.Builder signUpBuilder;

    private String userName;
    private String emptyUserName;
    private String incorrectEmail;
    private String correctEmail;
    private String emptyEmail;
    private String password;
    private String emptyPassword;


    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        userName = "testUserName";
        emptyUserName = "";
        incorrectEmail = "test";
        correctEmail = "test@test.com";
        emptyEmail = "";
        password = "password";
        emptyPassword = "";

// sign up
        SignUpComponent signUpComponent = new SignUpComponent() {
            @Override
            public void inject(SignUpActivity activity) {
                activity.presenter = new SignUpPresenter(appExecutors, userRepository);
                activity.analyticsManager = analyticsManager;
            }
        };
        when(signUpBuilder.module(Mockito.any(SignUpModule.class))).thenReturn(signUpBuilder);
        when(signUpBuilder.build()).thenReturn(signUpComponent);

        app.put(SignUpActivity.class, signUpBuilder);

    }

    @Test
    public void whenEmailIsEmptyShouldDisplayEmailError() {
        signUpScreen()
                .displayAsEntryPoint()
                .signUp(emptyUserName, emptyEmail, emptyPassword)
                .isEmailShouldBeValidErrorDisplayed();

    }

    @Test
    public void whenEmailIsNotCorrectShouldDisplayEmailError() {
        signUpScreen().
                displayAsEntryPoint()
                .signUp(emptyUserName, incorrectEmail, emptyPassword)
                .isEmailShouldBeValidErrorDisplayed();
    }

    @Test
    public void whenPasswordIsEmptyShouldDisplayPasswordError() {
        signUpScreen()
                .displayAsEntryPoint()
                .signUp(emptyUserName, correctEmail, emptyPassword)
                .isPasswordShouldNotBeEmptyErrorDisplayed();
    }

    @Test
    public void whenNameIsEmptyShouldDisplayNameError() {
        signUpScreen()
                .displayAsEntryPoint()
                .signUp(emptyUserName, correctEmail, password)
                .isNameShouldNotBeEmptyErrorDisplayed();
    }

    @Test
    public void whenSignUpErrorShouldDisplaySignUpError() {
        prepare(testScope)
                .mockSignUpError();

        signUpScreen()
                .displayAsEntryPoint()
                .signUp(userName, correctEmail, password)
                .isAccountCannotBeCreatedErrorDisplayed();
    }

    @Test
    public void whenSignUpSuccessShouldOpenMapScreen() {
        prepare(testScope)
                .mockLocationProvider(false)
                .mockSignUpSuccess(userName, correctEmail, password)
                .mockAuthorizedUser();

        signUpScreen()
                .displayAsEntryPoint()
                .signUp(userName, correctEmail, password);

        homeScreen()
                .isSuccessfullyLoaded();

    }


    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

}
