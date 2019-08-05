package ru.vpcb.map.notes.activity.home;

import ru.vpcb.map.notes.base.MvpView;

public interface HomeView extends MvpView {

    void updateMapInteractionMode(boolean isInteractionMode);

    void updateNavigationState(int newState);

    void displayAddNote();

    void displaySearchNotes();

    void hideContentWhichRequirePermissions();

    void showContentWhichRequirePermissions();

    void showPermissionExplanationSnackBar();

    void navigateToLoginScreen();

    boolean checkLocationPermission() ;

    void  requestLocationPermission();

    boolean shouldShowRequestPermission();

    boolean isOnline();

}
