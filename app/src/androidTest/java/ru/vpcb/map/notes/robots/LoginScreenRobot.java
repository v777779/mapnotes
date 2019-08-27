package ru.vpcb.map.notes.robots;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;

import ru.vpcb.map.notes.R;
import ru.vpcb.map.notes.activity.login.LoginActivity;

public class LoginScreenRobot extends BaseTestRobot {

    public static LoginScreenRobot loginScreen(){
        return new LoginScreenRobot();
    }


    public void  openSignIn(){
      clickOnView(R.id.signIn);
    }

    public  void  openSignUp() {
        clickOnView(R.id.signUp);
    }

    public void isSuccessfullyLoaded() {
        Intents.intended(IntentMatchers.hasComponent(LoginActivity.class.getName()));
    }

}
