package ru.vpcb.map.notes.activity.home;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import ru.vpcb.map.notes.MainApp;
import ru.vpcb.map.notes.MockTest;
import ru.vpcb.map.notes.di.HomeAdapter;
import ru.vpcb.map.notes.di.activity.home.HomeComponent;
import ru.vpcb.map.notes.di.activity.home.HomeModule;
import ru.vpcb.map.notes.fragments.add.AddNoteFragment;
import ru.vpcb.map.notes.fragments.add.AddNoteFragmentAccess;
import ru.vpcb.map.notes.fragments.add.AddNoteMvpPresenter;
import ru.vpcb.map.notes.fragments.add.AddNotePresenter;
import ru.vpcb.map.notes.fragments.search.SearchNotesFragment;
import ru.vpcb.map.notes.fragments.search.SearchNotesFragmentAccess;
import ru.vpcb.map.notes.fragments.search.SearchNotesMvpPresenter;
import ru.vpcb.map.notes.fragments.search.SearchNotesPresenter;
import ru.vpcb.map.notes.model.Note;

import static org.mockito.Mockito.when;
import static ru.vpcb.map.notes.robots.HomeScreenRobot.addNoteFragment;
import static ru.vpcb.map.notes.robots.HomeScreenRobot.homeScreen;
import static ru.vpcb.map.notes.robots.HomeScreenRobot.homeScreenMockActivityRule;
import static ru.vpcb.map.notes.robots.HomeScreenRobot.searchNoteFragment;
import static ru.vpcb.map.notes.robots.LoginScreenRobot.loginScreen;
import static ru.vpcb.map.notes.robots.PreparationRobot.prepare;

@RunWith(AndroidJUnit4.class)
public class HomeActivityTest extends MockTest {

    @Mock
    private HomeComponent.Builder homeBuilder;

    private String textNote;
    private String userUID;
    private String userName;

    @Rule
    public RuleChain chain = RuleChain.outerRule(permissionRule).around(homeScreenMockActivityRule);

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        textNote = "test note";
        userUID = "111111";
        userName = "testUserName";

// home
        HomeComponent homeComponent = new HomeAdapter() {
            @Override
            public void inject(HomeActivity activity) {
                HomeMvpPresenter presenter = new HomePresenter(appExecutors, userRepository);
                HomeActivityAccess.set(activity, presenter, mapFragment, analyticsManager);
            }

            @Override
            public void inject(AddNoteFragment fragment) {
                AddNoteMvpPresenter presenter = new AddNotePresenter(userRepository,
                        notesRepository, locationProvider, locationFormatter);
                AddNoteFragmentAccess.set(fragment, presenter);

            }

            @Override
            public void inject(SearchNotesFragment fragment) {
                SearchNotesMvpPresenter presenter = new SearchNotesPresenter(appExecutors,
                        userRepository, notesRepository);
                SearchNotesFragmentAccess.set(fragment, presenter);
            }
        };

        when(homeBuilder.module(Mockito.any(HomeModule.class))).thenReturn(homeBuilder);
        when(homeBuilder.build()).thenReturn(homeComponent);
        MainApp app = ApplicationProvider.getApplicationContext();
        app.put(HomeActivity.class, homeBuilder);

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
    public void shouldVerifySearchNoteFragment() {
        List<Note> notes = Collections.singletonList(new Note(0, 0,
                textNote, userUID));
        prepare(testScope)
                .mockLoadingListOfNotes(notes)
                .mockHumanReadableName(userName);

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
