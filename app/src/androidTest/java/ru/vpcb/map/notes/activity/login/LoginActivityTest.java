package ru.vpcb.map.notes.activity.login;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.vpcb.map.notes.MockTest;

import static ru.vpcb.map.notes.robots.LoginScreenRobot.loginScreen;
import static ru.vpcb.map.notes.robots.SignInScreenRobot.signInScreen;
import static ru.vpcb.map.notes.robots.SignUpScreenRobot.signUpScreen;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest extends MockTest {

    @Rule
    public ActivityTestRule<LoginActivity> activityRule =
            new ActivityTestRule<LoginActivity>(LoginActivity.class, true,false);

    @Before
    public void setUp() throws Exception {
        super.setUp();

        activityRule.launchActivity(null);
    }

    @Test
    public void whenClickToSignInButtonShouldLaunchSignInActivity() {
        loginScreen()
                .openSignIn();

        signInScreen()
                .isSuccessfullyLoaded();
    }

    @Test
    public void whenClickToSignUpButtonShouldLaunchSignUpActivity() {
        loginScreen()
                .openSignUp();

        signUpScreen()
                .isSuccessfullyLoaded();
    }


    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
