package ru.vpcb.map.notes.activity.login.signin;

import android.content.Context;
import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;

import ru.vpcb.map.notes.MockTest;

import static ru.vpcb.map.notes.robots.HomeScreenRobot.homeScreen;
import static ru.vpcb.map.notes.robots.PreparationRobot.prepare;
import static ru.vpcb.map.notes.robots.SignInScreenRobot.signInActivity;
import static ru.vpcb.map.notes.robots.SignInScreenRobot.signInScreen;

@RunWith(AndroidJUnit4.class)
public class SignInActivityTest extends MockTest {

//    @Rule
//    public ActivityTestRule<SignInActivity> activityRule =
//            new ActivityTestRule<>(SignInActivity.class, true, false);

    @Rule
    public RuleChain chain = RuleChain.outerRule(permissionRule).around(signInActivity);            // activityRule

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

//        activityRule.launchActivity(new Intent(InstrumentationRegistry.getInstrumentation()
//                .getTargetContext(), SignInActivity.class));
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

        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(context, SignInActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        signInScreen()
                .displayAsEntryPoint(intent)   // clearAndNavigateTo() use FLAG_ACTIVITY_CLEAR_TASK
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
