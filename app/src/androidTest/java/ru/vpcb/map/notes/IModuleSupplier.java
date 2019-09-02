package ru.vpcb.map.notes;

import android.app.Activity;

import ru.vpcb.map.notes.di.TestAppModule;
import ru.vpcb.map.notes.di.activity.home.HomeModule;

public interface IModuleSupplier {
    HomeModule apply(Activity activity);
    TestAppModule apply();
}
