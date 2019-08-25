package ru.vpcb.map.notes.robots;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.rule.ActivityTestRule;

import ru.vpcb.map.notes.activity.home.HomeActivity;

public class HomeScreenRobot {
    public static ActivityTestRule<HomeActivity> homeScreenMockActivityRule;

    public  static HomeScreenRobot homeScreen(){
        return new HomeScreenRobot();
    }

    static {
        homeScreenMockActivityRule = new ActivityTestRule<HomeActivity>(HomeActivity.class,
                true, false);
    }


    public void isSuccessfullyLoaded() {
        Intents.intended(IntentMatchers.hasComponent(HomeActivity.class.getName()));
    }

}
