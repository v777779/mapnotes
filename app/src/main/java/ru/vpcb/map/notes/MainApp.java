package ru.vpcb.map.notes;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import ru.vpcb.map.notes.di.AppComponent;
import ru.vpcb.map.notes.di.DaggerAppComponent;
import ru.vpcb.map.notes.di.activity.home.HomeComponent;
import ru.vpcb.map.notes.di.activity.home.HomeModule;
import ru.vpcb.map.notes.manager.FCManager;

public class MainApp extends Application {
    private AppComponent component;
    private HomeComponent homeComponent;

    private static MainApp getApplication(Activity activity) {
        if (activity == null) throw new NullPointerException();
        return (MainApp) activity.getApplication();
    }

    public static AppComponent get(Activity activity) {
        return getApplication(activity).getComponent();
    }

    public static HomeComponent plus(@NonNull Activity activity) {
        MainApp app = getApplication(activity);
        return app.getHomeComponent(activity);
    }

    public static void clear(AppCompatActivity activity) {
        MainApp app = getApplication(activity);
        app.clearHomeComponent();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FCManager.setup(this);                // Crashlytics
        initComponent();
    }

    private void initComponent() {
        component = DaggerAppComponent.builder()
                .build();

    }

    public AppComponent getComponent() {

        return component;
    }

    private HomeComponent getHomeComponent(Activity activity) {
        if (homeComponent == null)
            homeComponent = component.getHomeComponent(new HomeModule(activity));
        return homeComponent;
    }

    public void clearHomeComponent() {
        homeComponent = null;

    }


}
