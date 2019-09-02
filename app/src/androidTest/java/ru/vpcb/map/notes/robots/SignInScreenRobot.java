package ru.vpcb.map.notes.robots;

import android.content.Intent;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.rule.ActivityTestRule;

import ru.vpcb.map.notes.R;
import ru.vpcb.map.notes.activity.login.signin.SignInActivity;
import ru.vpcb.map.notes.idlingresources.ViewTextIdlingResource;

public class SignInScreenRobot extends BaseTestRobot {

    public static final ActivityTestRule<SignInActivity> signInActivity =
            new ActivityTestRule<>(SignInActivity.class,
                    true, false);

    public static SignInScreenRobot signInScreen(){
        return new SignInScreenRobot();
    }


    public SignInScreenRobot displayAsEntryPoint(Intent intent) {
        signInActivity.launchActivity(intent);
        return this;
    }
    public SignInScreenRobot displayAsEntryPoint() {
        signInActivity.launchActivity(null);
        return this;
    }

    public SignInScreenRobot signIn(String email, String password) {
        if (!email.isEmpty()) {
            enterText(R.id.email, email);
        }
        if (!password.isEmpty()) {
            enterText(R.id.password, password);
        }
        clickOnView(R.id.signIn);
        return this;
    }

    public void isSignInErrorDisplayed() {
        ViewTextIdlingResource snackbarErrorTextIdlingResource = new ViewTextIdlingResource(
                com.google.android.material.R.id.snackbar_text,
                R.string.error_user_cannot_be_authenticated);
        IdlingRegistry.getInstance().register(snackbarErrorTextIdlingResource);

        isTextDisplayed(R.string.error_user_cannot_be_authenticated);

        IdlingRegistry.getInstance().unregister(snackbarErrorTextIdlingResource);
    }

    public void isEmptyPasswordErrorDisplayed() {
        ViewTextIdlingResource snackBarErrorTextIdlingResource = new ViewTextIdlingResource(
                com.google.android.material.R.id.snackbar_text,
                R.string.error_password_should_not_be_empty);
        IdlingRegistry.getInstance().register(snackBarErrorTextIdlingResource);

        isTextDisplayed(R.string.error_password_should_not_be_empty);

        IdlingRegistry.getInstance().unregister(snackBarErrorTextIdlingResource);
    }

    public void isIncorrectEmailErrorDisplayed() {
        ViewTextIdlingResource snackBarErrorTextIdlingResource = new ViewTextIdlingResource(
                com.google.android.material.R.id.snackbar_text,
                R.string.error_email_should_be_valid);
        IdlingRegistry.getInstance().register(snackBarErrorTextIdlingResource);

        isTextDisplayed(R.string.error_email_should_be_valid);

        IdlingRegistry.getInstance().unregister(snackBarErrorTextIdlingResource);
    }

    public void isSuccessfullyLoaded() {
        Intents.intended(IntentMatchers.hasComponent(SignInActivity.class.getName()));
    }
}
