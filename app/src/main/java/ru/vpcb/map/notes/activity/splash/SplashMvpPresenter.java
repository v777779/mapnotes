package ru.vpcb.map.notes.activity.splash;

import ru.vpcb.map.notes.base.MvpPresenter;

public interface SplashMvpPresenter extends MvpPresenter<SplashView> {
    void start();

    void startMapNotes();

    void playMarketResults(int requestCode);

    void onPositive();

    void onNegative();
}
