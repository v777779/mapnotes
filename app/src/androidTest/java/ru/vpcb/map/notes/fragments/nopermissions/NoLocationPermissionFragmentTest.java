package ru.vpcb.map.notes.fragments.nopermissions;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.vpcb.map.notes.FragmentTestActivity;
import ru.vpcb.map.notes.MockTest;
import ru.vpcb.map.notes.R;
import ru.vpcb.map.notes.robots.TestActivityRobot;

import static ru.vpcb.map.notes.robots.NoLocationPermissionScreenRobot.noLocationPermissionScreen;
import static ru.vpcb.map.notes.robots.SystemAppPreferencesScreenRobot.systemAppPreferenceScreen;
import static ru.vpcb.map.notes.robots.TestActivityRobot.testScreen;

@RunWith(AndroidJUnit4.class)
public class NoLocationPermissionFragmentTest extends MockTest {

    @Rule
    public ActivityTestRule<FragmentTestActivity> activityRule
            = TestActivityRobot.testFragmentActivity;


    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        testScreen()
                .attachFragment(new NoLocationPermissionFragment());

    }

    @Test
    public void shouldVerifyLayoutOfFragment() {
        noLocationPermissionScreen()
                .isSuccessfullyLoaded();

    }

    @Test
//    @Ignore
    public void shouldVerifyOpeningAppPreferences() {
        String appName = activityRule.getActivity().getString(R.string.app_name);
        noLocationPermissionScreen()
                .openApplicationPreferences();
        systemAppPreferenceScreen()
                .isAppPreferencesDisplayed(appName)
                .closeAppPreferenceScreen();
    }

    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
