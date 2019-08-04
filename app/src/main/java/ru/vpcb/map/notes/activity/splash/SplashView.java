package ru.vpcb.map.notes.activity.splash;

import ru.vpcb.map.notes.base.MvpView;

public interface SplashView extends MvpView {
    void getErrorDialog(int code);

    void getAlertDialog();

    int codeGooglePlayServices();

    boolean isInstalledPlayMarket();

    void finishView();

    void navigateToLogin();

    void navigateToHome();

    void navigateToPlayMarket();

    boolean isAuthenticated();

}