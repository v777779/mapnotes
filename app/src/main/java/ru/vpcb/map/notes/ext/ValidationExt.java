package ru.vpcb.map.notes.ext;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Matcher;


public class ValidationExt {

    public static boolean isValidEmail(String s) {
        if(TextUtils.isEmpty(s)){
            return false;
        }
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
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = cm.getActiveNetwork();
            if (network == null) {
                return false;
            }
            NetworkCapabilities capabilities = cm.getNetworkCapabilities(network);
            LinkProperties properties = cm.getLinkProperties(network);
            if (capabilities == null) {
                return false;
            }
            return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        }else{
            android.net.NetworkInfo netInfo = cm.getActiveNetworkInfo(); // suppress import deprecated
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }

    }


}
