package ru.vpcb.map.notes.manager;

import android.content.Context;
import android.text.TextUtils;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;


public class FCManager {

    // static
    synchronized public static void log(Throwable t) {
        if (t == null) return;
        Crashlytics.logException(t);
    }

    synchronized public static void log(String s) {
        if (TextUtils.isEmpty(s)) return;
        Crashlytics.log(s);
    }

    // under synchronized
    public static void setup(Context context) {
        if (Fabric.isInitialized()) return;
        Fabric.with(context, new Crashlytics());
    }

    // test
    public static void forceCrash() {
        Crashlytics.getInstance().crash();
    }


}
