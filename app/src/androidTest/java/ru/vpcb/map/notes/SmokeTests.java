package ru.vpcb.map.notes;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ru.vpcb.map.notes.robots.SplashScreenRobot;

import static ru.vpcb.map.notes.robots.HomeScreenRobot.addNoteFragment;
import static ru.vpcb.map.notes.robots.HomeScreenRobot.homeScreen;
import static ru.vpcb.map.notes.robots.HomeScreenRobot.searchNoteFragment;
import static ru.vpcb.map.notes.robots.LoginScreenRobot.loginScreen;
import static ru.vpcb.map.notes.robots.SignInScreenRobot.signInScreen;
import static ru.vpcb.map.notes.robots.SplashScreenRobot.splashScreen;

@RunWith(AndroidJUnit4.class)
public class SmokeTests {

    @Rule
    public GrantPermissionRule permissionRule =
            GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    @Rule
    public RuleChain chain = RuleChain
            .outerRule(permissionRule)
            .around(SplashScreenRobot.splashActivityE2ETestRule);

    private String correctEmail;
    private String correctPassword;
    private String incorrectPassword;

    @Before
    public void setUp() throws Exception {
        correctEmail = "test@test.com";
        correctPassword = "test123";
        incorrectPassword = "test-password";

        Intents.init();     // espresso intents
    }

    @Test
    public void shouldVerifySuccessfulLogin() {
        splashScreen()
                .displayAsEntryPoint();
        homeScreen()
                .safeSignOut();
       loginScreen()
                .openSignIn();
        signInScreen()
                .signIn(correctEmail, correctPassword);
        homeScreen()
                .isMapDisplayed()
                .signOut();
    }

    @Test
    public void shouldVerifyFailureLogin() {
        splashScreen()
                .displayAsEntryPoint();
        homeScreen()
                .safeSignOut();
        loginScreen()
                .openSignIn();
        signInScreen()
                .signIn(correctEmail, incorrectPassword)
                .isSignInErrorDisplayed();
    }

    @Test
    public void shouldVerifyAddingAndSearchNote() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
        String noteText = format.format( Calendar.getInstance().getTime());
        splashScreen()
                .displayAsEntryPoint();
        homeScreen()
                .safeSignOut();
        loginScreen()
                .openSignIn();
        signInScreen()
                .signIn(correctEmail, correctPassword);
        homeScreen()
                .isMapDisplayed()
                .openAddNote();
        addNoteFragment()
                .addNote(noteText);
        homeScreen()
                .openSearch();
        searchNoteFragment()
                .searchNoteByText(noteText)
                .isSearchResultsHaveNoteWithTitle(noteText);
        homeScreen().
                signOut();
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
    }
}
