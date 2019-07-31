package ru.vpcb.map.notes.ext;

import android.app.Activity;
import android.content.Intent;

public class NavigationExt {

    public static void navigateTo(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
    }

    public static void clearAndNavigateTo(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls).setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
    }
}
