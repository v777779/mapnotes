package ru.vpcb.map.notes.idlingresources;

import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.IdlingResource;

import ru.vpcb.map.notes.CurrentActivity;

public class RecyclerViewSizeIdlingResources implements IdlingResource {


    private IdlingResource.ResourceCallback resourceCallback;
    private int expectedViewId;

    public RecyclerViewSizeIdlingResources(@IdRes int expectedViewId ) {  // just Lint warning
        this.expectedViewId = expectedViewId;
        this.resourceCallback = null;
    }

    @Override
    public String getName() {
        return RecyclerViewSizeIdlingResources.class.getSimpleName();
    }

    @Override
    public boolean isIdleNow() {
        RecyclerView view = CurrentActivity.getIdlingResourceActivityInstance()
                .findViewById(expectedViewId);
        boolean isIdle = false;
        if(view != null && view.getAdapter()!= null) {
            isIdle = view.getAdapter().getItemCount() > 0;
        }

// TODO Check what is this
        if(isIdle && resourceCallback!= null){
            resourceCallback.onTransitionToIdle();  //???
        }

        return isIdle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        resourceCallback = callback;
    }


}
