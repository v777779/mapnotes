package ru.vpcb.map.notes.di;

import android.app.Activity;

public interface ActivityComponent<A extends Activity> {
    void inject(A activity);
}
