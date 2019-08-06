package ru.vpcb.map.notes.ext;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Patterns;

import java.util.regex.Matcher;

public class ValidationExt {

    public static boolean isValidEmail(String s) {
        Matcher matcher = Patterns.EMAIL_ADDRESS.matcher(s);
        return matcher.matches();
    }

    /**
     * Returns status of connection to network
     *
     * @param context Context of calling activity
     * @return boolean status of connection, true if connected, false if not
     */

    @SuppressWarnings("deprecation")
    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
