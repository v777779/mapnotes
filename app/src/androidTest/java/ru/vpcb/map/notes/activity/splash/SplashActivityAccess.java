package ru.vpcb.map.notes.activity.splash;

public class SplashActivityAccess {

    public static SplashMvpPresenter get(SplashActivity activity){
        if(activity == null)return null;
        return activity.presenter;
    }
}
