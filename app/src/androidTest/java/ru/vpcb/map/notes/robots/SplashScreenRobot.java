package ru.vpcb.map.notes.robots;

import androidx.test.rule.ActivityTestRule;

import ru.vpcb.map.notes.activity.splash.SplashActivity;

public class SplashScreenRobot extends BaseTestRobot {
    public static final ActivityTestRule<SplashActivity> splashActivityE2ETestRule =
            new ActivityTestRule<SplashActivity>(SplashActivity.class,
                    true, false) {
                @Override
                public void beforeActivityLaunched() {
// TODO Dagger Support Check MainApp Load Modules
//                StandAloneContext.loadKoinModules(listOf(appModule))  // dagger support
                    super.beforeActivityLaunched();
                }
            };

    public static final ActivityTestRule<SplashActivity> splashActivityMockTestRule =
            new ActivityTestRule<SplashActivity>(SplashActivity.class,
                    true, false);


    public static SplashScreenRobot splashScreen() {
        return new SplashScreenRobot();
    }

    public void displayAsEntryPoint() {
        splashActivityE2ETestRule.launchActivity(null);
    }

    public void displayMockAsEntryPoint() {
        splashActivityMockTestRule.launchActivity(null);
    }
}

// alternative
//    private static class LazyHolder {
//        private static final SplashScreenRobot INSTANCE = new SplashScreenRobot();
//    }
//
//    public static SplashScreenRobot splashScreen() {
//        return LazyHolder.INSTANCE;
//    }
