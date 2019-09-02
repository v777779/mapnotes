package ru.vpcb.map.notes.di;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import ru.vpcb.map.notes.di.activity.ActivityScope;

@Module
public abstract class ActivityModule<T extends Activity> {
    protected T activity;

    public ActivityModule(T activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    T provideActivity() {
        return activity;
    }

}
