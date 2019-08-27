package ru.vpcb.map.notes.robots;

import ru.vpcb.map.notes.R;

public class NoLocationPermissionScreenRobot extends BaseTestRobot {

    public static NoLocationPermissionScreenRobot noLocationPermissionScreen() {
        return new NoLocationPermissionScreenRobot();
    }

    public void openApplicationPreferences() {
        clickOnView(R.id.openAppPrefs);
    }

    public void isSuccessfullyLoaded() {
        isViewDisplayed(R.id.mapImage);
        isViewWithTextDisplayed(R.id.permissionExplanation, R.string.permission_explanation);
        isViewWithTextDisplayed(R.id.openAppPrefs, R.string.open_app_prefs);
        isViewEnabled(R.id.openAppPrefs);
    }
}
