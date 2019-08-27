package ru.vpcb.map.notes.robots;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

import org.junit.Assert;

public class SystemAppPreferencesScreenRobot extends BaseTestRobot {
    public static SystemAppPreferencesScreenRobot systemAppPreferenceScreen(){
        return new SystemAppPreferencesScreenRobot();
    }

    public void closeAppPreferenceScreen() {
        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        uiDevice.pressBack();
    }

    public SystemAppPreferencesScreenRobot isAppPreferencesDisplayed(String expectedAppName) {
        String expectedAppNameRes = "android:id/title";
        long timeout = 5_000L;

        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        uiDevice.wait(Until.hasObject(By.text(expectedAppName)), timeout);
        UiObject2 appName = uiDevice.findObject(By.res(expectedAppNameRes));
        Assert.assertEquals(expectedAppName, appName.getText());

        return this;
    }
}
