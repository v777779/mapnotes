package ru.vpcb.test.map.home;

import ru.vpcb.test.map.base.MvpPresenter;

public interface HomeMvpPresenter extends MvpPresenter<HomeView> {
    boolean handleNavigationItemClick(int itemId);

    void showLocationPermissionRationale();

    void checkUser();

    void signOut();
}
