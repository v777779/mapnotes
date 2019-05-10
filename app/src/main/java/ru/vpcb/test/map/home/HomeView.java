package ru.vpcb.test.map.home;

import ru.vpcb.test.map.base.MvpView;

public interface HomeView extends MvpView {

    void updateMapInteractionMode(boolean isInteractionMode);

    void updateNavigationState(int newState);

    void displayAddNote();

    void displaySearchNotes();

    void hideContentWhichRequirePermissions();

    void showContentWhichRequirePermissions();

    void showPermissionExplanationSnackBar();

    void navigateToLoginScreen();
}
