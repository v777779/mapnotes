package ru.vpcb.map.notes.manager;

import android.text.TextUtils;
import android.util.Log;

public class FCManager {
    private static final String TAG = "FCManager";

    public static void log(String tag, String s) {
        if (TextUtils.isEmpty(s)) return;
        if (TextUtils.isEmpty(tag)) tag = TAG;
        Log.d(tag, s);
    }

    public static void log(String s) {
        log(TAG, s);
    }
}
