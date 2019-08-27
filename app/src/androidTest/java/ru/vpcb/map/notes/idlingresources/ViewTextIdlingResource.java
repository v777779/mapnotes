package ru.vpcb.map.notes.idlingresources;

import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import androidx.test.espresso.IdlingResource;

import ru.vpcb.map.notes.CurrentActivity;

public class ViewTextIdlingResource implements IdlingResource {

    private ResourceCallback resourceCallback;
    private int expectedViewId;
    private int expectedViewText;

    public ViewTextIdlingResource(@IdRes int expectedViewId, @StringRes int expectedViewText) {         // @IdRes for Lint
        this.expectedViewId = expectedViewId;
        this.expectedViewText = expectedViewText;
        this.resourceCallback = null;
    }

    @Override
    public String getName() {
        return ViewTextIdlingResource.class.getSimpleName();
    }

    @Override
    public boolean isIdleNow() {
        boolean isIdle = false;
        TextView view = CurrentActivity.getIdlingResourceActivityInstance()
                .findViewById(expectedViewId);
        String expectedText = CurrentActivity.getIdlingResourceActivityInstance()
                .getString(expectedViewText);
        if (view != null) {
            isIdle = view.getText().toString().equals(expectedText);
        }
        if (isIdle && resourceCallback != null) {
            resourceCallback.onTransitionToIdle();
        }
        return isIdle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        resourceCallback = callback;
    }
}
