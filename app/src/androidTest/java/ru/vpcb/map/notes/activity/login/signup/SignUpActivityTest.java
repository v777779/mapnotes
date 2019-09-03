package ru.vpcb.map.notes.activity.login.signup;

import android.content.Context;
import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.vpcb.map.notes.MockTest;

import static ru.vpcb.map.notes.robots.HomeScreenRobot.homeScreen;
import static ru.vpcb.map.notes.robots.PreparationRobot.prepare;
import static ru.vpcb.map.notes.robots.SignUpScreenRobot.signUpScreen;

@RunWith(AndroidJUnit4.class)
public class SignUpActivityTest extends MockTest {

//    @Rule
//    public RuleChain chain = RuleChain.outerRule(permissionRule).around(signUpActivity); // activityRule

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

        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(context, SignUpActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        signUpScreen()
                .displayAsEntryPoint(intent)
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
