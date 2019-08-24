package ru.vpcb.map.notes.robots;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.RootMatchers;

import org.hamcrest.Matchers;

import ru.vpcb.map.notes.matchers.BottomNavigationMatchers;
import ru.vpcb.map.notes.matchers.RecyclerViewMatchers;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;


public class BaseTestRobot {
    protected ViewInteraction enterText(int viewId, String text) {
        return onView(withId(viewId)).perform(replaceText(text), closeSoftKeyboard());
    }

    private ViewInteraction clickOnView(int viewId) {
        return onView(withId(viewId)).perform(click());
    }

    private ViewInteraction clickOnViewWithText(int textId) {
        return onView(withText(textId))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    private ViewInteraction changeSelectedSpinnerItemPosition(int position) {
        return onData(Matchers.anything())
                .atPosition(position)
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());
    }

    private ViewInteraction isViewDisplayed(int viewId) {
        return onView(withId(viewId))
                .check(matches(isDisplayed()));
    }

    private ViewInteraction isTextDisplayed(int textId) {
        return onView(withText(textId))
                .check(matches(isDisplayed()));
    }


    private ViewInteraction isViewWithTextDisplayed(int viewId, String text) {
        return onView(withId(viewId))
                .check(matches(withText(text)));
    }


    private ViewInteraction isViewWithTextDisplayed(int viewId, int textId) {
        return onView(withId(viewId))
                .check(matches(withText(textId)));
    }


    private ViewInteraction isViewWithJintDisplayed(int viewId, int textId) {
        return onView(withId(viewId))
                .check(matches(withHint(textId)));
    }


    private ViewInteraction isSpinnerHasText(int viewId, int textId) {
        return onView(withId(viewId))
                .check(matches(withSpinnerText(textId)));
    }

    private ViewInteraction isViewEnabled(int viewId) {
        return onView(withId(viewId))
                .check(matches(isEnabled()));
    }

    private ViewInteraction isViewDisabled(int viewId) {
        return onView(withId(viewId))
                .check(matches(not(isEnabled())));
    }


    private ViewInteraction isRecyclerViewHasItemWithText(int viewId, String text) {
        return onView(withId(viewId))
                .check(matches(RecyclerViewMatchers.withItemText(text)));
    }

    private ViewInteraction isRecyclerViewItemCount(int viewId, int count) {
        return onView(withId(viewId))
                .check(matches(RecyclerViewMatchers.withItemCount(count)));
    }

    private ViewInteraction isBottomNavigationHasCheckedItemId(int viewId, int itemId) {
        return onView(withId(viewId))
                .check(matches(BottomNavigationMatchers.hasCheckedItem(itemId)));
    }

    private ViewInteraction isBottomNavigationItemCount(int viewId, int count) {
        return onView(withId(viewId))
                .check(matches(BottomNavigationMatchers.withItemCount(count)));
    }

    private ViewInteraction isBottomNavigationHasItemTitle(int viewId, String title) {
        return onView(withId(viewId))
                .check(matches(BottomNavigationMatchers.hasItemTitle(title)));
    }

}
