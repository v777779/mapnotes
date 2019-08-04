package ru.vpcb.map.notes.activity.splash;

import androidx.annotation.NonNull;

import com.google.android.gms.common.ConnectionResult;

import static com.google.android.gms.common.ConnectionResult.SUCCESS;
import static ru.vpcb.map.notes.activity.splash.SplashActivity.GPS_REQUEST_CODE;

public class SplashPresenter implements SplashMvpPresenter {
    private SplashView view;


    private static class LazyHolder {
        private static final SplashPresenter INSTANCE = new SplashPresenter();
    }

    public static SplashPresenter getInstance() {
        return LazyHolder.INSTANCE;
    }


    @Override
    public void onAttach(@NonNull SplashView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }

    @Override
    public void checkGoogleServices() {
        if (view == null) return;
        int code = view.isPlayServicesAvailable();

        if (code == ConnectionResult.SUCCESS) {
            startMapNotes();
        }
        if (view.isInstalledPlayMarket()) {
            view.getErrorDialog(code);          // play market
        } else {
            view.getAlertDialog();              // no play market
        }
    }

    @Override
    public void startMapNotes() {
        if (view == null) return;
        if (view.isAuthenticated()) {
            view.navigateToHome();
        } else {
            view.navigateToLogin();
        }
    }

    @Override
    public void playMarketResults(int requestCode) {
        if (view == null) return;
        if (requestCode == GPS_REQUEST_CODE) {
            if (view.isPlayServicesAvailable() == SUCCESS) {
                startMapNotes();
            } else {
                view.finishView();
            }
        }
    }

    @Override
    public void onPositive() {
        if (view == null) {
            return;
        }
        view.navigateToPlayMarket();
    }

    @Override
    public void onNegative() {
        if (view == null) {
            return;
        }
        view.finishView();
    }


}
