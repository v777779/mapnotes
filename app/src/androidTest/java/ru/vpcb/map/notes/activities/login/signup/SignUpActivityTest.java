package ru.vpcb.map.notes.activities.login.signup;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;

import ru.vpcb.map.notes.MockTest;

import static ru.vpcb.map.notes.robots.HomeScreenRobot.homeScreen;
import static ru.vpcb.map.notes.robots.PreparationRobot.prepare;
import static ru.vpcb.map.notes.robots.SignUpScreenRobot.signUpActivity;
import static ru.vpcb.map.notes.robots.SignUpScreenRobot.signUpScreen;

@RunWith(AndroidJUnit4.class)
public class SignUpActivityTest extends MockTest {
    @Rule
    public RuleChain chain = RuleChain.outerRule(permissionRule).around(signUpActivity);

    private String username;
    private String emptyUsername;
    private String incorrectEmail;
    private String correctEmail;
    private String emptyEmail;
    private String password;
    private String emptyPassword;


    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        username = "testUserName";
        emptyUsername = "";
        incorrectEmail = "test";
        correctEmail = "test@test.com";
        emptyEmail = "";
        password = "password";
        emptyPassword = "";

    }

    @Test
    public void whenEmailIsEmptyShouldDisplayEmailError() {
        signUpScreen()
                .displayAsEntryPoint()
                .signUp(emptyUsername, emptyEmail, emptyPassword)
                .isEmailShouldBeValidErrorDisplayed();

    }

    @Test
    public void whenEmailIsNotCorrectShouldDisplayEmailError() {
        signUpScreen().
                displayAsEntryPoint()
                .signUp(emptyUsername, incorrectEmail, emptyPassword)
                .isEmailShouldBeValidErrorDisplayed();
    }

    @Test
    public void whenPasswordIsEmptyShouldDisplayPasswordError() {
        signUpScreen()
                .displayAsEntryPoint()
                .signUp(emptyUsername, correctEmail, emptyPassword)
                .isPasswordShouldNotBeEmptyErrorDisplayed();
    }

    @Test
    public void whenNameIsEmptyShouldDisplayNameError() {
        signUpScreen()
                .displayAsEntryPoint()
                .signUp(emptyUsername, correctEmail, password)
                .isNameShouldNotBeEmptyErrorDisplayed();
    }

    @Test
    public void whenSignUpErrorShouldDisplaySignUpError() {
        prepare(testScope)
                .mockSignUpError();

        signUpScreen()
                .displayAsEntryPoint()
                .signUp(username, correctEmail, password)
                .isAccountCannotBeCreatedErrorDisplayed();
    }

    @Test
    public void whenSignUpSuccessShouldOpenMapScreen() {
        prepare(testScope)
                .mockLocationProvider(false)
                .mockSignUpSuccess(username, correctEmail, password)
                .mockAuthorizedUser();

        signUpScreen()
                .displayAsEntryPoint()
                .signUp(username, correctEmail, password);

        homeScreen()
                .isSuccessfullyLoaded();

    }


    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

}
