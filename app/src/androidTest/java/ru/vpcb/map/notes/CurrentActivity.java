package ru.vpcb.map.notes;

import android.app.Activity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;

import java.util.Collection;

public class CurrentActivity extends Activity {

    /*
        Get current activity from Espresso  https://testyour.app/blog/get-current-activity
     */
    public static Activity getActivityInstance() {
        final Activity[] activities = {null};

// TODO check on NullPointerException

        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            Collection<Activity> resumedActivities =
                    ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
            if (resumedActivities.iterator().hasNext()) {
                activities[0] = resumedActivities.iterator().next();
            }
        });
        return activities[0];
    }

// TODO check on NullPointerException

    public static Activity getIdlingResourceActivityInstance() {
        final Activity[] activities = {null};
        Collection<Activity> resumedActivities = ActivityLifecycleMonitorRegistry.getInstance()
                .getActivitiesInStage(Stage.RESUMED);

            if (resumedActivities.iterator().hasNext()) {
                activities[0] = resumedActivities.iterator().next();
            }

        return activities[0];
    }

}
