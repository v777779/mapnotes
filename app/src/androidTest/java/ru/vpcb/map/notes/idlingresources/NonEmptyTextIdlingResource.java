package ru.vpcb.map.notes.idlingresources;

import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.test.espresso.IdlingResource;

import ru.vpcb.map.notes.CurrentActivity;

public class NonEmptyTextIdlingResource implements IdlingResource {

    private ResourceCallback resourceCallback;
    private int expectedViewId;

    public NonEmptyTextIdlingResource(@IdRes int expectedViewId) {         // @IdRes for Lint
        this.expectedViewId = expectedViewId;
        this.resourceCallback = null;
    }

    @Override
    public String getName() {
        return NonEmptyTextIdlingResource.class.getSimpleName();
    }

    @Override
    public boolean isIdleNow() {
        boolean isIdle = false;
        TextView view = CurrentActivity.getIdlingResourceActivityInstance()
                .findViewById(expectedViewId);

        if (view != null) {
            isIdle = !TextUtils.isEmpty(view.getText().toString())              // empty
                    && !TextUtils.isEmpty(view.getText().toString().trim());    // blank
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
