package ru.vpcb.map.notes.matchers;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Locale;

public class BottomNavigationMatchers {

    public static Matcher<View> hasCheckedItem(int itemId) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                BottomNavigationView bottomNavigationView = (BottomNavigationView) item;
                Menu menu = bottomNavigationView.getMenu();
                for (int i = 0; i < menu.size(); i++) {
                    MenuItem menuItem = menu.getItem(i);
                    if (menuItem.isChecked()) {
                        return menuItem.getItemId() == itemId;
                    }
                }
                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(String.format(Locale.ENGLISH,
                        "BottomNavigationView should have checked item with id: %d", itemId));
            }
        };
    }

    public static Matcher<View> withItemCount(int count) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                BottomNavigationView bottomNavigationView = (BottomNavigationView) item;
                Menu menu = bottomNavigationView.getMenu();
                return menu.size() == count;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(String.format(Locale.ENGLISH,
                        "BottomNavigationView should have %d item", count));

            }
        };
    }

    public static Matcher<View> hasItemTitle(String text) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                BottomNavigationView bottomNavigationView = (BottomNavigationView) item;
                Menu menu = bottomNavigationView.getMenu();
                for (int i = 0; i < menu.size(); i++) {
                    MenuItem menuItem = menu.getItem(i);
                    if (menuItem == null || menuItem.getTitle() == null) continue;
                    if (menuItem.getTitle().toString().contains(text)) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(String.format(
                        "BottomNavigationView should have item with text: %s", text));
            }
        };
    }

}
