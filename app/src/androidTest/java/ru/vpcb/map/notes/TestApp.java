package ru.vpcb.map.notes;

import android.app.Activity;

import ru.vpcb.map.notes.activity.home.HomeActivity;
import ru.vpcb.map.notes.di.AppComponent;
import ru.vpcb.map.notes.di.DaggerAppComponent;
import ru.vpcb.map.notes.di.activity.home.HomeComponent;

public class TestApp extends MainApp {

    private AppComponent component;
    private HomeComponent homeComponent;
    private IModuleSupplier supplier;


    @Override
    public void initComponent() {

    }

    @Override
    public AppComponent getComponent() {
        if(component == null){
            component = DaggerAppComponent.builder()
                    .appModule(supplier.apply())
                    .build();
        }
        return component;
    }

    @Override
    public HomeComponent getHomeComponent(Activity activity) {
        if(homeComponent == null) {
            homeComponent = getComponent().getHomeComponent(supplier.apply((HomeActivity)activity));
        }
        return homeComponent;
    }

    @Override
    public void clearHomeComponent() {
        homeComponent = null;
    }


    public void setSupplier(IModuleSupplier supplier){
        this.supplier = supplier;
    }

    public void clearAll(){
        component = null;
        homeComponent= null;
        supplier = null;
    }
}
