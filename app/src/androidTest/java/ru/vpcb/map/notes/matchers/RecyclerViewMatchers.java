package ru.vpcb.map.notes.matchers;

import android.view.View;

import org.hamcrest.Matcher;

public class RecyclerViewMatchers {

    public static Matcher<View> withItemCount(int count) {

        return null;
    }

//    fun withItemCount(count: Int): Matcher<View> {
//        return object : TypeSafeMatcher<View>() {
//            override fun matchesSafely(view: View): Boolean {
//                val recyclerView = view as RecyclerView
//                val adapter = recyclerView.adapter
//                return adapter?.itemCount == count
//            }
//
//            override fun describeTo(description: Description) {
//                description.appendText("RecyclerView should have $count items")
//            }
//        }
//    }

    public static Matcher<View> atPosition(int position, Matcher<View> itemMatcher) {

        return null;
    }

//
//    fun atPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View> {
//        checkNotNull(itemMatcher)
//        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
//            override fun describeTo(description: Description) {
//                description.appendText("has item at position $position: ")
//                itemMatcher.describeTo(description)
//            }
//
//            override fun matchesSafely(view: RecyclerView): Boolean {
//                val viewHolder = view.findViewHolderForAdapterPosition(position)
//                        ?: return false
//                return itemMatcher.matches(viewHolder.itemView)
//            }
//        }
//    }

    public static Matcher<View> withItemText(String text) {

        return null;
    }

//    fun withItemText(text: String): Matcher<View> {
//        return object : TypeSafeMatcher<View>() {
//            override fun matchesSafely(view: View): Boolean {
//                val recyclerView = view as RecyclerView
//                val adapter = recyclerView.adapter
//                val itemCount = adapter?.itemCount!!
//                for (pos in 0..itemCount) {
//                    val viewHolder = recyclerView.findViewHolderForAdapterPosition(pos) as NoteViewHolder
//                    val noteTextView = viewHolder.itemView.findViewById<TextView>(R.id.noteText)
//                    if (noteTextView.text.toString() == text) {
//                        return true
//                    }
//                }
//                return false
//            }
//
//            override fun describeTo(description: Description) {
//                description.appendText("RecyclerView should have item with text: $text")
//            }
//        }
//    }
}
