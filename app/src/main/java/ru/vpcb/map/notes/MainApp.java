package ru.vpcb.map.notes;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.multidex.MultiDex;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import ru.vpcb.map.notes.activity.home.HomeActivity;
import ru.vpcb.map.notes.di.ActivityComponentBuilder;
import ru.vpcb.map.notes.di.AppComponent;
import ru.vpcb.map.notes.di.DaggerAppComponent;
import ru.vpcb.map.notes.di.IBuilderProvider;
import ru.vpcb.map.notes.di.activity.home.HomeComponent;
import ru.vpcb.map.notes.di.activity.home.HomeModule;

/*
    MultiDex Support fro API17
    extends MultiDexApplication or
    call MultiDex.install() in attachBaseContext()  used here

    Crashlytics init is not necessary
    it initialized automatically by hook in Manifest
 */
public class MainApp extends Application implements IBuilderProvider {
    private AppComponent component;
    private HomeComponent homeComponent;

    @Inject
    Map<Class<? extends Activity>, Provider<ActivityComponentBuilder>> map;

    public static IBuilderProvider provider(@NonNull Context context) {
        return (IBuilderProvider) context.getApplicationContext();
    }

    public static AppComponent get(@NonNull Activity activity) {
        return ((MainApp)activity.getApplication()).getComponent();
    }

    public static HomeComponent plus(@NonNull Activity activity) {
        return ((MainApp)activity.getApplication()).getHomeComponent(activity);
    }

    public static void clear(@NonNull Activity activity) {
        ((MainApp)activity.getApplication()).clearHomeComponent();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.create();
        component.inject(this);
    }

    public AppComponent getComponent() {

        return component;
    }

    public HomeComponent getHomeComponent(Activity activity) {
        if (homeComponent == null) {
            HomeComponent.Builder builder = (HomeComponent.Builder) provide(HomeActivity.class);
            if(builder == null)return null;
            homeComponent = builder
                    .module(new HomeModule(activity))
                    .build();
        }
        return homeComponent;
    }

    public void clearHomeComponent() {
        homeComponent = null;

    }

    @Override
    public ActivityComponentBuilder provide(Class<? extends  Activity> activityClass) {
        Provider<ActivityComponentBuilder> provider = map.get(activityClass);
        if(provider == null)return null;
        return provider.get();
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public void put(Class<? extends Activity> c, ActivityComponentBuilder builder) {
        Map<Class<? extends Activity>, Provider<ActivityComponentBuilder>> map = new HashMap<>(this.map);
        map.put(c, () -> builder);
        this.map = map;
    }
}

// alternative
//    public void initComponent() {
//        component = DaggerAppComponent.builder()
//                .appModule(new AppModule()) //can be skip
//                .build();
//        component.inject(this);
//    }

