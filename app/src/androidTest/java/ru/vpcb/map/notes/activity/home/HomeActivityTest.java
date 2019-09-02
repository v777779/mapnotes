package ru.vpcb.map.notes.activity.home;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.List;

import ru.vpcb.map.notes.MockTest;
import ru.vpcb.map.notes.model.Note;
import ru.vpcb.map.notes.robots.HomeScreenRobot;

import static ru.vpcb.map.notes.robots.HomeScreenRobot.addNoteFragment;
import static ru.vpcb.map.notes.robots.HomeScreenRobot.homeScreen;
import static ru.vpcb.map.notes.robots.HomeScreenRobot.searchNoteFragment;
import static ru.vpcb.map.notes.robots.LoginScreenRobot.loginScreen;
import static ru.vpcb.map.notes.robots.PreparationRobot.prepare;

@RunWith(AndroidJUnit4.class)
public class HomeActivityTest extends MockTest {

    // TODO check for what ???
    @Rule
    public RuleChain chain = RuleChain
            .outerRule(permissionRule)
            .around(HomeScreenRobot.homeScreenMockActivityRule);

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        prepare(testScope)
                .mockLocationProvider(true)
                .mockAuthorizedUser();
    }

    @Test
    public void shouldVerifyAllTabs() {
        homeScreen()
                .displayAsEntryPoint()
                .isSuccessfullyDisplayed();
    }

    @Test
    public void shouldVerifyAddNoteFragment() {
        homeScreen()
                .displayAsEntryPoint()
                .openAddNote();
        addNoteFragment()
                .isSuccessfullyDisplayedAddNote();
    }

    @Test
//    @Ignore  // TODO check for what
    public void shouldVerifySearchNoteFragment() {
        List<Note> notes = Collections.singletonList(new Note(0, 0,
                "test note", "test user"));
        prepare(testScope)
                .mockLoadingListOfNotes(notes);

        homeScreen()
                .displayAsEntryPoint()
                .isSuccessfullyDisplayed()
                .openSearch();
        searchNoteFragment()
                .isSuccessfullyDisplayedSearch();
    }

    @Test
    public void whenMovingFromAddTabShouldVerifyMapFragment() {
        homeScreen()
                .displayAsEntryPoint()
                .openAddNote()
                .openMap();

    }

    @Test
    public void whenSignOutShouldOpenLoginScreen() {
        prepare(testScope)
                .mockUserSignOut();

        homeScreen()
                .displayAsEntryPoint()
                .signOut();

        loginScreen()
                .isSuccessfullyLoaded();
    }


    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }


}
