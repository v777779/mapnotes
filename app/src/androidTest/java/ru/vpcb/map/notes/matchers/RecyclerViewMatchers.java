package ru.vpcb.map.notes.matchers;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.matcher.BoundedMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Locale;

import ru.vpcb.map.notes.R;
import ru.vpcb.map.notes.fragments.search.adapter.NoteViewHolder;

public class RecyclerViewMatchers {

    public static Matcher<View> withItemCount(int count) {

        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                RecyclerView recyclerView = (RecyclerView) item;
                RecyclerView.Adapter adapter = recyclerView.getAdapter();
                return (adapter != null && adapter.getItemCount() == count);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(String.format(Locale.ENGLISH,
                        "RecyclerView should have %d items", count));
            }
        };
    }


    public static Matcher<View> atPosition(int position, Matcher<View> itemMatcher) {

        if (itemMatcher == null) return null;
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText(String.format(Locale.ENGLISH,
                        "has item at position: %d ", position));
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(RecyclerView item) {
                RecyclerView.ViewHolder viewHolder = item.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }


    public static Matcher<View> withItemText(String text) {

        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                RecyclerView recyclerView = (RecyclerView) item;
                RecyclerView.Adapter adapter = recyclerView.getAdapter();

                int itemCount = (adapter == null) ? 0 : adapter.getItemCount();
                for (int pos = 0; pos < itemCount; pos++) {

                    RecyclerView.ViewHolder viewHolder = recyclerView
                            .findViewHolderForAdapterPosition(pos);
                    if (!(viewHolder instanceof NoteViewHolder)) {
                        continue;
                    }
                    TextView noteTextView = viewHolder.itemView.findViewById(R.id.noteText);
                    if (noteTextView != null && noteTextView.getText().toString().equals(text)) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(
                        String.format("RecyclerView should have item with text: %s",text));
            }
        };
    }
}
