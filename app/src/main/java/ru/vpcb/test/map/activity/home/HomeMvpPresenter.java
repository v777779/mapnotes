package ru.vpcb.test.map.activity.home;

import ru.vpcb.test.map.base.MvpPresenter;

public interface HomeMvpPresenter extends MvpPresenter<HomeView> {
    boolean handleNavigationItemClick(int itemId);

    void checkEnablePermissions();

    void checkUser();

    void signOut();

}
