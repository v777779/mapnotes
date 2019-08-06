package ru.vpcb.map.notes.activity.splash;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.common.GoogleApiAvailability;

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


public class SplashActivity extends BaseActivity implements SplashView {
    private static final String GooglePlayStorePackageNameOld = "com.google.market";
    private static final String GooglePlayStorePackageNameNew = "com.android.vending";
    public static int GPS_REQUEST_CODE = 20001;

    @Inject
    UserRepository authRepository;

    private SplashMvpPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = SplashPresenter.getInstance();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.start();

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (presenter != null) {
            presenter.onAttach(this);
        }
    }

    @Override
    protected void onStop() {
        if (presenter != null) {
            presenter.onDetach();
        }
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (presenter != null) {
            presenter.playMarketResults(requestCode);
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


    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void navigateToLogin() {
        NavigationExt.navigateTo(this, LoginActivity.class);
    }

    @Override
    public void navigateToHome() {
        NavigationExt.navigateTo(this, HomeActivity.class);
    }

    @Override
    public void navigateToPlayMarket() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" +
                            GoogleApiAvailability.GOOGLE_PLAY_SERVICES_PACKAGE)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" +
                            GoogleApiAvailability.GOOGLE_PLAY_SERVICES_PACKAGE)));
        }
        finishActivity();
    }

    @Override
    public boolean isAuthenticated() {
        return (authRepository.getCurrentUser() instanceof Result.Success);
    }


    @Override
    public boolean isInstalledPlayMarket() {
        PackageManager pm = getApplication().getPackageManager();
        List<PackageInfo> packages;
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N){
            packages = getInstalledPackages();
        }else {
            packages = matchInstalledPackages();
        }
        for (PackageInfo info : packages) {
            if (info.packageName.equals(GooglePlayStorePackageNameOld) ||
                    info.packageName.equals(GooglePlayStorePackageNameNew)) {
                String label = (String) info.applicationInfo.loadLabel(pm);
                return (!TextUtils.isEmpty(label) && label.startsWith("Google Play"));
            }
        }
        return false;
    }

    @Override
    public int isPlayServicesAvailable() {
        return GoogleApiAvailability.getInstance()
                .isGooglePlayServicesAvailable(this);
    }

    @Override
    public void getAlertDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage(R.string.update_services_message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (presenter != null) {
                            presenter.onPositive();
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (presenter != null) {
                            presenter.onNegative();
                        }
                    }
                }).create();

        dialog.show();
    }


    @Override
    public void getErrorDialog(int code) {
        /*
         * Google Play Services is missing or update is required
         *  return code could be
         * SUCCESS,
         * SERVICE_MISSING, SERVICE_VERSION_UPDATE_REQUIRED,
         * SERVICE_DISABLED, SERVICE_INVALID.
         */
        GoogleApiAvailability.getInstance().getErrorDialog(this, code, GPS_REQUEST_CODE,
                dialogInterface -> {
                    if (presenter != null) {
                        presenter.onNegative();
                    }
                }
        ).show();
    }

    @SuppressWarnings("deprecation")
    private List<PackageInfo> getInstalledPackages() {
        PackageManager pm = getApplication().getPackageManager();
        return pm.getInstalledPackages(
                PackageManager.GET_UNINSTALLED_PACKAGES);

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private List<PackageInfo> matchInstalledPackages() {
        PackageManager pm = getApplication().getPackageManager();
       return pm.getInstalledPackages(
                PackageManager.MATCH_UNINSTALLED_PACKAGES);

    }


}
