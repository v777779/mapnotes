package ru.vpcb.map.notes.robots;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;

import java.util.List;

import ru.vpcb.map.notes.R;
import ru.vpcb.map.notes.idlingresources.RecyclerViewSizeIdlingResources;
import ru.vpcb.map.notes.matchers.RecyclerViewMatchers;
import ru.vpcb.map.notes.model.Note;

public class HomeSearchNoteRobot extends BaseTestRobot {
    private int searchUserCategoryPosition;

    private HomeSearchNoteRobot() {
        this.searchUserCategoryPosition = 1;
    }

    static HomeSearchNoteRobot searchNoteFragment() {
        return new HomeSearchNoteRobot();
    }

    public HomeSearchNoteRobot searchNoteByText(String text) {
        enterText(R.id.searchText, text);
        clickOnView(R.id.searchButton);
        return this;
    }

    public HomeSearchNoteRobot searchNoteByUser(String text) {
        enterText(R.id.searchText, text);
        clickOnView(R.id.searchOptions);
        changeSelectedSpinnerItemPosition(searchUserCategoryPosition);
        clickOnView(R.id.searchButton);

        return this;
    }

    // TODO Check this
    public void isSearchResultsHaveNoteWithTitle(String noteText) {
        RecyclerViewSizeIdlingResources recyclerViewIdlingResource =
                new RecyclerViewSizeIdlingResources(R.id.recyclerView);
        IdlingRegistry.getInstance().register(recyclerViewIdlingResource);
        isRecyclerViewHasItemWithText(R.id.recyclerView, noteText);
        IdlingRegistry.getInstance().unregister(recyclerViewIdlingResource);
    }


    public void isSearchResultsHaveNotes(List<Note> notes) {
        RecyclerViewSizeIdlingResources recyclerViewIdlingResource =
                new RecyclerViewSizeIdlingResources(R.id.recyclerView);
        IdlingRegistry.getInstance().register(recyclerViewIdlingResource);
        for (int index = 0; index < notes.size() - 1; index++) {
            Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
                    .check(ViewAssertions
                            .matches(RecyclerViewMatchers.atPosition(index,
                                    ViewMatchers.hasDescendant(
                                            ViewMatchers.withText(notes.get(index).getText()))))
                    );
        }
        IdlingRegistry.getInstance().unregister(recyclerViewIdlingResource);
    }

    public void isErrorDuringLoadingNotesDisplayed() {
        isTextDisplayed(R.string.loading_notes_error);
    }

    public void isUnknownUserErrorDisplayed() {
        isTextDisplayed(R.string.unknown_user_error);
    }

    public HomeSearchNoteRobot isSearchResultHasNumberItems(int itemCount) {
        isRecyclerViewItemCount(R.id.recyclerView, itemCount);
        return this;
    }

    public void isSuccessfullyDisplayedSearchScreen() {
        isViewDisplayed(R.id.recyclerView);
        isViewHintDisplayed(R.id.searchText, R.string.search_hint);
        isViewDisplayed(R.id.searchOptions);
        isSpinnerHasText(R.id.searchOptions, R.string.search_notes_category);
        isViewWithTextDisplayed(R.id.searchButton, R.string.search_button_text);
    }

    public void isSuccessfullyDisplayedSearch() {
        isViewHintDisplayed(R.id.searchText, R.string.search_hint);
        isViewDisplayed(R.id.searchButton);
    }

}
