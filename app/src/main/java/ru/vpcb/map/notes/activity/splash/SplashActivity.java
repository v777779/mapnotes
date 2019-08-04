package ru.vpcb.map.notes.activity.splash;

import android.app.Activity;
import android.app.Instrumentation;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.util.List;

import javax.inject.Inject;

import ru.vpcb.map.notes.MainApp;
import ru.vpcb.map.notes.R;
import ru.vpcb.map.notes.activity.BaseActivity;
import ru.vpcb.map.notes.activity.home.HomeActivity;
import ru.vpcb.map.notes.activity.login.LoginActivity;
import ru.vpcb.map.notes.data.Result;
import ru.vpcb.map.notes.data.repository.UserRepository;
import ru.vpcb.map.notes.ext.NavigationExt;
import ru.vpcb.map.notes.manager.FCManager;

import static com.google.android.gms.common.ConnectionResult.SERVICE_MISSING;
import static com.google.android.gms.common.ConnectionResult.SUCCESS;


public class SplashActivity extends BaseActivity {
    private static final String GooglePlayStorePackageNameOld = "com.google.market";
    private static final String GooglePlayStorePackageNameNew = "com.android.vending";
    private static int GPS_REQUEST_CODE = 20001;

    @Inject
    UserRepository authRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!checkGooglePlayServices()) {
            return;
        }
        startMapNotes();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GPS_REQUEST_CODE) {
            if (stateGooglePlayServices() == SUCCESS) {
                startMapNotes();
            } else {
                finish();
            }
        }
    }

    @Override
    public void setupComponent() {
        try {
            MainApp.get(this)
                    .getSplashComponent()
                    .inject(this);
        } catch (Exception e) {
            FCManager.log(e);
        }
    }

    private void startMapNotes() {
        if (isAuthenticated()) {
            NavigationExt.navigateTo(this, HomeActivity.class);
        } else {
            NavigationExt.navigateTo(this, LoginActivity.class);
        }
        finish();
    }

    // TODO Presenter
    private boolean isAuthenticated() {
        return (authRepository.getCurrentUser() instanceof Result.Success);
    }

    // TODO Presenter
    private void getErrorDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage(R.string.update_services_message)
                .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + GoogleApiAvailability.GOOGLE_PLAY_SERVICES_PACKAGE)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + GoogleApiAvailability.GOOGLE_PLAY_SERVICES_PACKAGE)));
                        }
                        finish();
                    }
                })
                .setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).create();

        dialog.show();
    }

    private boolean isInstalledPlayMarket() {
        PackageManager pm = getApplication().getPackageManager();
        List<PackageInfo> packages = pm.getInstalledPackages(
                PackageManager.GET_UNINSTALLED_PACKAGES);
        for (PackageInfo info : packages) {
            if (info.packageName.equals(GooglePlayStorePackageNameOld) ||
                    info.packageName.equals(GooglePlayStorePackageNameNew)) {
                String label = (String) info.applicationInfo.loadLabel(pm);
                return (!TextUtils.isEmpty(label) && label.startsWith("Google Play"));
            }
        }
        return false;
    }

    private int stateGooglePlayServices() {
        return GoogleApiAvailability.getInstance()
                .isGooglePlayServicesAvailable(this);
    }

    private boolean checkGooglePlayServices() {
        boolean isMarket = isInstalledPlayMarket();
        int code = stateGooglePlayServices();

        if (code != ConnectionResult.SUCCESS) {
            /*
             * Google Play Services is missing or update is required
             *  return code could be
             * SUCCESS,
             * SERVICE_MISSING, SERVICE_VERSION_UPDATE_REQUIRED,
             * SERVICE_DISABLED, SERVICE_INVALID.
             */
            if (isMarket) {
                GoogleApiAvailability.getInstance().getErrorDialog(this, code,
                        GPS_REQUEST_CODE, dialogInterface -> {
                            finish();
                        }
                ).show();
            } else {
                getErrorDialog();
            }
            return false;
        }

        return true;
    }


}
