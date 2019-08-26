package ru.vpcb.map.notes.idlingresources;

import android.view.View;

import androidx.annotation.IdRes;
import androidx.test.espresso.IdlingResource;

import ru.vpcb.map.notes.CurrentActivity;

public class ViewVisibilityIdlingResource implements IdlingResource {

    private IdlingResource.ResourceCallback resourceCallback;
    private  int expectedViewId;
    private int expectedViewVisibility;

    public ViewVisibilityIdlingResource(@IdRes int expectedViewId, int expectedViewVisibility) {
        this.expectedViewId = expectedViewId;
        this.expectedViewVisibility = expectedViewVisibility;
    }

    @Override
    public String getName() {
        return ViewVisibilityIdlingResource.class.getSimpleName();
    }

    @Override
    public boolean isIdleNow() {
        View view = CurrentActivity.getIdlingResourceActivityInstance()
                .findViewById(expectedViewId);
        boolean isIdle = false;
        if (view != null) {
            isIdle = view.getVisibility() == expectedViewVisibility;
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

