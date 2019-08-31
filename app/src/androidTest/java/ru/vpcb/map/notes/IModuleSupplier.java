package ru.vpcb.map.notes;

import android.app.Activity;

import ru.vpcb.map.notes.activity.home.HomeActivity;
import ru.vpcb.map.notes.di.AppModule;
import ru.vpcb.map.notes.di.TestAppModule;
import ru.vpcb.map.notes.di.activity.home.HomeModule;

public interface IModuleSupplier {
    HomeModule apply(HomeActivity activity);
    TestAppModule apply();
}
