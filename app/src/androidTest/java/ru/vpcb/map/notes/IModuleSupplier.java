package ru.vpcb.map.notes;

import android.app.Activity;

import ru.vpcb.map.notes.di.AppModule;
import ru.vpcb.map.notes.di.activity.home.HomeModule;

public interface IModuleSupplier {
    HomeModule apply(Activity activity);
    AppModule apply();
}
