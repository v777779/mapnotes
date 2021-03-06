package ru.vpcb.map.notes.activity.home;

import ru.vpcb.map.notes.base.MvpPresenter;

public interface HomeMvpPresenter extends MvpPresenter<HomeView> {
    boolean handleNavigationItemClick(int itemId);

    void showLocationPermissionRationale();

    void showLocationRequirePermissions();

    void checkUser();

    void signOut();

}
