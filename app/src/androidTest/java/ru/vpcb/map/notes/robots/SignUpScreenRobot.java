package ru.vpcb.map.notes.robots;

import android.content.Intent;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.rule.ActivityTestRule;

import ru.vpcb.map.notes.R;
import ru.vpcb.map.notes.activity.login.signup.SignUpActivity;

public class SignUpScreenRobot extends BaseTestRobot {

    public static final ActivityTestRule<SignUpActivity> signUpActivity =
            new ActivityTestRule<SignUpActivity>(SignUpActivity.class,
                    true, false);

    public static SignUpScreenRobot signUpScreen() {
        return new SignUpScreenRobot();
    }

    public SignUpScreenRobot displayAsEntryPoint(Intent intent) {
        signUpActivity.launchActivity(intent);
        return this;
    }

    public SignUpScreenRobot displayAsEntryPoint() {
        signUpActivity.launchActivity(null);
        return this;
    }

    public SignUpScreenRobot signUp(String name, String email, String password) {
        if (!name.isEmpty()) {
            enterText(R.id.name, name);
        }
        if (!email.isEmpty()) {
            enterText(R.id.email, email);
        }
        if (!password.isEmpty()) {
            enterText(R.id.password, password);
        }
        clickOnView(R.id.signUp);
        return this;
    }

    public void isSuccessfullyLoaded() {
        Intents.intended(IntentMatchers.hasComponent(SignUpActivity.class.getName()));
    }

    public void isEmailShouldBeValidErrorDisplayed() {
        isTextDisplayed(R.string.error_email_should_be_valid);
    }

    public void isPasswordShouldNotBeEmptyErrorDisplayed() {
        isTextDisplayed(R.string.error_password_should_not_be_empty);
    }

    public void isNameShouldNotBeEmptyErrorDisplayed() {
        isTextDisplayed(R.string.error_name_should_not_be_empty);
    }

    public void isAccountCannotBeCreatedErrorDisplayed() {
        isTextDisplayed(R.string.error_account_cannot_be_created);
    }
}
