package ru.vpcb.map.notes.robots;

import android.view.View;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.rule.ActivityTestRule;

import ru.vpcb.map.notes.CurrentActivity;
import ru.vpcb.map.notes.R;
import ru.vpcb.map.notes.activity.home.HomeActivity;
import ru.vpcb.map.notes.idlingresources.ViewVisibilityIdlingResource;

public class HomeScreenRobot extends BaseTestRobot {
    public static final ActivityTestRule<HomeActivity> homeScreenMockActivityRule =
            new ActivityTestRule<>(HomeActivity.class, true, false);

    public static HomeScreenRobot homeScreen() {
        return new HomeScreenRobot();
    }

    public static HomeAddNoteRobot addNoteFragment() {
        return HomeAddNoteRobot.addNoteFragment();
    }

    public static HomeSearchNoteRobot searchNoteFragment() {
        return HomeSearchNoteRobot.searchNoteFragment();
    }


    public HomeScreenRobot displayAsEntryPoint() {
        homeScreenMockActivityRule.launchActivity(null);
        return this;
    }

    public HomeScreenRobot openAddNote() {
        clickOnView(R.id.navigation_add_note);
        isBottomNavigationHasCheckedItemId(R.id.navigation, R.id.navigation_add_note);
        return this;
    }

    public void openMap() {
        clickOnView(R.id.navigation_map);
        isBottomNavigationHasCheckedItemId(R.id.navigation, R.id.navigation_map);
    }

    public HomeScreenRobot openSearch() {
        clickOnView(R.id.navigation_search_notes);
        isBottomNavigationHasCheckedItemId(R.id.navigation, R.id.navigation_search_notes);
        return this;
    }

    public void signOut() {
        Espresso.openActionBarOverflowOrOptionsMenu(CurrentActivity.getActivityInstance());
        clickOnViewWithText(R.string.nav_sign_out_title);
    }

    public void isMapDisplayed() {
        ViewVisibilityIdlingResource mapVisibilityIdlingResource =
                new ViewVisibilityIdlingResource(R.id.mapContainer, View.VISIBLE);
        IdlingRegistry.getInstance().register(mapVisibilityIdlingResource);

        isViewDisplayed(R.id.mapContainer);
        IdlingRegistry.getInstance().unregister(mapVisibilityIdlingResource);

    }

    public HomeScreenRobot isSuccessfullyDisplayed() {
        isBottomNavigationItemCount(R.id.navigation, 3);
        isBottomNavigationHasItemTitle(R.id.navigation,
                CurrentActivity.getActivityInstance().getString(R.string.nav_add_note_title));
        isBottomNavigationHasItemTitle(R.id.navigation,
                CurrentActivity.getActivityInstance().getString(R.string.nav_map_title));
        isBottomNavigationHasItemTitle(R.id.navigation,
                CurrentActivity.getActivityInstance().getString(R.string.nav_search_notes_title));
        isBottomNavigationHasCheckedItemId(R.id.navigation, R.id.navigation_map);
        return this;
    }


    public void isSuccessfullyLoaded() {
        Intents.intended(IntentMatchers.hasComponent(HomeActivity.class.getName()));
    }

}
