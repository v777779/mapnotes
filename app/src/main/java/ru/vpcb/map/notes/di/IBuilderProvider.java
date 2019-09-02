package ru.vpcb.map.notes.di;

import android.app.Activity;

public interface IBuilderProvider {
    ActivityComponentBuilder provide(Class<? extends Activity> c);
}
