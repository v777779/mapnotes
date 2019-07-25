package ru.vpcb.test.map;

import android.app.Application;

import ru.vpcb.test.map.di.AppComponent;
import ru.vpcb.test.map.di.AppModule;
import ru.vpcb.test.map.di.DaggerAppComponent;

public class MainApp extends Application {
    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        initComponent();
    }

    private void initComponent(){
        component = DaggerAppComponent.builder()
                .build();

    }

    public AppComponent getComponent(){
        return component;
    }
}
