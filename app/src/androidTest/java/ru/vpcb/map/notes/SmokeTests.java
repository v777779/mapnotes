package ru.vpcb.map.notes;

import android.app.Activity;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;

import java.util.Calendar;

import ru.vpcb.map.notes.di.AppModule;
import ru.vpcb.map.notes.di.activity.home.HomeModule;
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

        IModuleSupplier supplier = new IModuleSupplier() {
            @Override
            public HomeModule apply(Activity activity) {
                return new HomeModule(activity);
            }

            @Override
            public AppModule apply() {
                return new AppModule();
            }
        };

        TestApp app = ApplicationProvider.getApplicationContext();
        app.setSupplier(supplier);


        Intents.init();     // espresso intents
    }

    @Test
    public void shouldVerifySuccessfulLogin() {
        splashScreen()
                .displayAsEntryPoint();
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
        loginScreen()
                .openSignIn();
        signInScreen()
                .signIn(correctEmail, incorrectPassword)
                .isSignInErrorDisplayed();
    }

    @Test
    public void shouldVerifyAddingAndSearchNote() {
        String noteText = String.format("test note %s", Calendar.getInstance().getTime());
        splashScreen()
                .displayAsEntryPoint();
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
