package ru.vpcb.map.notes;

import android.app.Activity;

import ru.vpcb.map.notes.di.AppComponent;
import ru.vpcb.map.notes.di.DaggerAppComponent;
import ru.vpcb.map.notes.di.TestAppModule;
import ru.vpcb.map.notes.di.activity.home.HomeComponent;
import ru.vpcb.map.notes.di.activity.home.TestHomeModule;

public class TestMainApp extends MainApp {

    private AppComponent component;
    private HomeComponent homeComponent;


    @Override
    public void initComponent() {
        component = DaggerAppComponent.builder()
                .appModule(new TestAppModule())
                .build();
    }

    @Override
    public AppComponent getComponent() {
        return component;
    }

    @Override
    public HomeComponent getHomeComponent(Activity activity) {
        if(homeComponent == null) {
            homeComponent = component.getHomeComponent(new TestHomeModule(activity));
        }
        return homeComponent;
    }

    @Override
    public void clearHomeComponent() {
        homeComponent = null;
    }
}
