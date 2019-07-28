package ru.vpcb.test.map;

import android.app.Activity;
import android.app.Application;

import ru.vpcb.test.map.di.AppComponent;
import ru.vpcb.test.map.di.DaggerAppComponent;

public class MainApp extends Application {
    private AppComponent component;

    public static AppComponent get(Activity activity) {
        if (activity == null) return null;
        return ((MainApp)activity.getApplication()).getComponent();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initComponent();
    }

    private void initComponent() {
        component = DaggerAppComponent.builder()
                .build();

    }

    public AppComponent getComponent() {
        return component;
    }


}
