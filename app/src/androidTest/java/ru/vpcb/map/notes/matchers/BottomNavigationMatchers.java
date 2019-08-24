package ru.vpcb.map.notes.matchers;

import android.view.View;

import org.hamcrest.Matcher;

public class BottomNavigationMatchers {

    public static Matcher<View> hasCheckedItem(int itemId) {
        return null;
    }

    public static Matcher<View> withItemCount(int count) {
        return null;
    }

    public static Matcher<View> hasItemTitle(String text) {
        return null;
    }

//    fun hasCheckedItem(itemId: Int): Matcher<View> {
//        return object : TypeSafeMatcher<View>() {
//            override fun matchesSafely(view: View): Boolean {
//                val bottomNavigationView = view as BottomNavigationView
//                val menu = bottomNavigationView.menu
//                for (i in 0 until menu.size()) {
//                    val item = menu.getItem(i)
//                    if (item.isChecked) {
//                        return item.itemId == itemId
//                    }
//                }
//                return false
//            }
//
//            override fun describeTo(description: Description) {
//                description.appendText("BottomNavigationView should have checked item with id: $itemId")
//            }
//        }
//    }
//
//    fun withItemCount(count: Int): Matcher<View> {
//        return object : TypeSafeMatcher<View>() {
//            override fun matchesSafely(view: View): Boolean {
//                val bottomNavigationView = view as BottomNavigationView
//                val menu = bottomNavigationView.menu
//                return menu.size() == count
//            }
//
//            override fun describeTo(description: Description) {
//                description.appendText("BottomNavigationView should have $count item")
//            }
//        }
//    }
//
//    fun hasItemTitle(text: String): Matcher<View> {
//        return object : TypeSafeMatcher<View>() {
//            override fun matchesSafely(view: View): Boolean {
//                val bottomNavigationView = view as BottomNavigationView
//                val menu = bottomNavigationView.menu
//                for (i in 0 until menu.size()) {
//                    val item = menu.getItem(i)
//                    if (item.title.contains(text)) {
//                        return true
//                    }
//                }
//                return false
//            }
//
//            override fun describeTo(description: Description) {
//                description.appendText("BottomNavigationView should have item with text: $text")
//            }
//        }
//    }

}
