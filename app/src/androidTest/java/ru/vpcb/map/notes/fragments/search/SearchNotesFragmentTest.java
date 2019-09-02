package ru.vpcb.map.notes.fragments.search;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import ru.vpcb.map.notes.FragmentTestActivity;
import ru.vpcb.map.notes.MockTest;
import ru.vpcb.map.notes.model.Note;
import ru.vpcb.map.notes.robots.TestActivityRobot;

import static ru.vpcb.map.notes.robots.HomeScreenRobot.searchNoteFragment;
import static ru.vpcb.map.notes.robots.PreparationRobot.prepare;
import static ru.vpcb.map.notes.robots.TestActivityRobot.testScreen;

@RunWith(AndroidJUnit4.class)
public class SearchNotesFragmentTest extends MockTest {


    @Rule
    public ActivityTestRule<FragmentTestActivity> activityRule = TestActivityRobot.testFragmentActivity;

    private List<Note> testNotes;
    private String searchInput;
    private String emptySearchInput;
    private String testUID;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        searchInput = "text";
        emptySearchInput = "";
        testUID = "1111111";

        testNotes = Arrays.asList(
                new Note(0, 0, "test note 1_1", "11111111"),
                new Note(0, 0, "test note 2_1", "11111111"),
                new Note(0, 0, "test note 1_2", "22222222"),
                new Note(0, 0, "test note 2_2", "22222222"),
                new Note(0, 0, "test note 3_2", "22222222"));
    }

    @Test
    public void shouldVerifyLayout() {
        prepare(testScope)
                .mockLoadingListOfNotesEmpty();

        testScreen()
                .attachFragment(new SearchNotesFragment());
        searchNoteFragment()
                .isSuccessfullyDisplayedSearchScreen();
    }

    @Test
    public void shouldDisplayNotes() {
        int expectedItemCount = testNotes.size();

        prepare(testScope)
                .mockLoadingListOfNotes(testNotes);

        testScreen()
                .attachFragment(new SearchNotesFragment());
        searchNoteFragment()
                .isSearchResultHasNumberItems(expectedItemCount)
                .isSearchResultsHaveNotes(testNotes);
    }

    @Test
    public void shouldDisplayUnknownUserUser() {
        prepare(testScope)
                .mockLoadingListOfNotesEmpty()
                .mockErrorDuringLoadingUserNames();
        testScreen()
                .attachFragment(new SearchNotesFragment());
        searchNoteFragment()
                .searchNoteByUser(searchInput)
                .isUnknownUserErrorDisplayed();
    }

    @Test
    public void shouldSearchByUserAndDisplayResults() {
        int expectedItemCount = testNotes.size();
        prepare(testScope)
                .mockLoadingListOfNotesEmpty()
                .mockSearchUserId(testUID)
                .mockSearchNoteByAnyUser(testNotes);
        testScreen()
                .attachFragment(new SearchNotesFragment());
        searchNoteFragment()
                .searchNoteByUser(searchInput)
                .isSearchResultHasNumberItems(expectedItemCount)
                .isSearchResultsHaveNotes(testNotes);

    }

    @Test
    public void shouldSearchByNotesAndDisplayResult() {
        int expectedItemCount = testNotes.size();
        prepare(testScope)
                .mockLoadingListOfNotesEmpty()
                .mockSearchNoteByAnyText(testNotes);
        testScreen()
                .attachFragment(new SearchNotesFragment());
        searchNoteFragment()
                .searchNoteByText(searchInput)
                .isSearchResultHasNumberItems(expectedItemCount)
                .isSearchResultsHaveNotes(testNotes);
    }

    @Test
    public void shouldSearchIncorrectDataAndDisplayCorrectDataAfterClearRequest() {
        int expectedItemCount = testNotes.size();

        prepare(testScope)
                .mockLoadingListOfNotes(testNotes)
                .mockLoadingListOfNotesByNoteTextEmpty()
                .mockLoadingListOfNotes(testNotes);
        testScreen()
                .attachFragment(new SearchNotesFragment());
        searchNoteFragment()
                .searchNoteByText(searchInput)
                .searchNoteByText(emptySearchInput)
                .isSearchResultHasNumberItems(expectedItemCount)
                .isSearchResultsHaveNotes(testNotes);
    }


    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

}
