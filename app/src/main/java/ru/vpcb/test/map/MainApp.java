package ru.vpcb.test.map;

import android.app.Activity;
import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;

import ru.vpcb.test.map.di.AppComponent;
import ru.vpcb.test.map.di.DaggerAppComponent;
import ru.vpcb.test.map.di.activity.home.HomeComponent;
import ru.vpcb.test.map.di.activity.home.HomeModule;

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

    public static HomeComponent plus(AppCompatActivity activity) {
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

        initComponent();
    }

    private void initComponent() {
        component = DaggerAppComponent.builder()
                .build();

    }

    public AppComponent getComponent() {

        return component;
    }

    private HomeComponent getHomeComponent(AppCompatActivity activity) {
        if (homeComponent == null)
            homeComponent = component.getHomeComponent(new HomeModule(activity));
        return homeComponent;
    }

    public void clearHomeComponent() {
        homeComponent = null;

    }


}
