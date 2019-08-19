package ru.vpcb.map.notes.fragments.map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import ru.vpcb.map.notes.MainApp;
import ru.vpcb.map.notes.model.Location;
import ru.vpcb.map.notes.model.Note;

@RunWith(RobolectricTestRunner.class)   // setup Robolectric  build.gradle, gradle.properties
@Config(
        sdk = 28,
        application = MainApp.class
)
public class GoogleMapPresenterTests {
    private Location currentLocation;
    private Location newLocation;
    private Note testNote;
    String authUserId;
    String testNoteText;

    @Mock
    private MapView view;

    private MapMvpPresenter presenter;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        currentLocation = new Location(-33.8688, 151.2093);
        newLocation = new Location(-37.4220, -122.0840);
        testNoteText = "test note";
        authUserId = "111111";
        testNote = new Note(currentLocation.getLatitude(), currentLocation.getLongitude(),
                testNoteText, authUserId);

        presenter = new GoogleMapPresenter();

        Mockito.doAnswer(invocation -> null).when(view).animateCamera(currentLocation);
        Mockito.doAnswer(invocation -> null).when(view).displayNoteOnMap(testNote);
        Mockito.doAnswer(invocation -> null).when(view).showLocationAlertDialog();
        Mockito.doAnswer(invocation -> null).when(view).openSettings();
        Mockito.doAnswer(invocation -> null).when(view).exit();
        Mockito.doAnswer(invocation -> null).when(view).sendAnalytics(currentLocation);
        Mockito.doAnswer(invocation -> null).when(view).sendAnalytics(testNote);

        Mockito.when(view.isLocationAvailable()).thenReturn(true);

    }

    // 0    handleInteractionMode   interaction mode
    @Test
    public void handleInteractionModeInteractionModeWithNonNullViewAnimateCameraNotCalled() {
        presenter.setCurrentLocation(currentLocation);
        presenter.onAttach(view);
        presenter.handleInteractionMode(true);

        Mockito.verify(view, Mockito.times(0)).animateCamera(currentLocation);
        Mockito.verify(view, Mockito.times(0)).sendAnalytics(currentLocation);
    }

    @Test
    public void handleInteractionModeInteractionModeWithNullViewAnimateCameraNotCalled() {
        presenter.setCurrentLocation(currentLocation);
        presenter.onAttach(null);
        presenter.handleInteractionMode(true);

        Mockito.verify(view, Mockito.times(0)).animateCamera(currentLocation);
        Mockito.verify(view, Mockito.times(0)).sendAnalytics(currentLocation);
    }

    @Test
    public void handleInteractionModeInteractionModeWithViewDetachedAnimateCameraNotCalled() {
        presenter.setCurrentLocation(currentLocation);
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.handleInteractionMode(true);

        Mockito.verify(view, Mockito.times(0)).animateCamera(currentLocation);
        Mockito.verify(view, Mockito.times(0)).sendAnalytics(currentLocation);
    }

// 1    handleInteractionMode   not interaction mode

    @Test
    public void handleInteractionModeNotInteractionModeWithNonNullViewAnimateCameraCalled() {
        presenter.setCurrentLocation(currentLocation);
        presenter.onAttach(view);
        presenter.handleInteractionMode(false);

        Mockito.verify(view, Mockito.times(1)).animateCamera(currentLocation);
        Mockito.verify(view, Mockito.times(1)).sendAnalytics(currentLocation);
    }

    @Test
    public void handleInteractionModeNotInteractionModeWithNullViewAnimateCameraNotCalled() {
        presenter.setCurrentLocation(currentLocation);
        presenter.onAttach(null);
        presenter.handleInteractionMode(false);

        Mockito.verify(view, Mockito.times(0)).animateCamera(currentLocation);
        Mockito.verify(view, Mockito.times(0)).sendAnalytics(currentLocation);
    }

    @Test
    public void handleInteractionModeNotInteractionModeWithViewDetachedAnimateCameraNotCalled() {
        presenter.setCurrentLocation(currentLocation);
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.handleInteractionMode(false);

        Mockito.verify(view, Mockito.times(0)).animateCamera(currentLocation);
        Mockito.verify(view, Mockito.times(0)).sendAnalytics(currentLocation);
    }

// 2    handleMapNote   not interaction mode new location not equals to current

    @Test
    public void handleMapNoteWithNonNullViewDisplayNoteOnMapCalled() {
        presenter.onAttach(view);
        presenter.handleMapNote(testNote);

        Mockito.verify(view, Mockito.times(1)).displayNoteOnMap(testNote);
        Mockito.verify(view, Mockito.times(1)).sendAnalytics(testNote);
    }

    @Test
    public void handleMapNoteWithNullViewDisplayNoteOnMapNotCalled() {
        presenter.onAttach(null);
        presenter.handleMapNote(testNote);

        Mockito.verify(view, Mockito.times(0)).displayNoteOnMap(testNote);
        Mockito.verify(view, Mockito.times(0)).sendAnalytics(testNote);
    }

    @Test
    public void handleMapNoteWithViewDetachedDisplayNoteOnMapNotCalled() {
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.handleMapNote(testNote);

        Mockito.verify(view, Mockito.times(0)).displayNoteOnMap(testNote);
        Mockito.verify(view, Mockito.times(0)).sendAnalytics(testNote);
    }

// 3    handleLocationUpdate   not interaction mode new location not equals to current

    @Test
    public void handleLocationUpdateNotInteractionModeNewLocationNotEqualsToCurrentWithNonNullViewAnimateCameraCalled() {
        presenter.setCurrentLocation(currentLocation);
        presenter.onAttach(view);
        presenter.handleLocationUpdate(false, newLocation);

        Mockito.verify(view, Mockito.times(1)).animateCamera(newLocation);
        Mockito.verify(view, Mockito.times(1)).sendAnalytics(newLocation);
        Assert.assertEquals(presenter.getCurrentLocation(),newLocation);
    }

    @Test
    public void handleLocationUpdateNotInteractionModeNewLocationNotEqualsToCurrentWithNullViewAnimateCameraNotCalled() {
        presenter.setCurrentLocation(currentLocation);
        presenter.onAttach(null);
        presenter.handleLocationUpdate(false, newLocation);

        Mockito.verify(view, Mockito.times(0)).animateCamera(newLocation);
        Mockito.verify(view, Mockito.times(0)).sendAnalytics(newLocation);
        Assert.assertNotEquals(presenter.getCurrentLocation(),newLocation);

    }

    @Test
    public void handleLocationUpdateNotInteractionModeNewLocationNotEqualsToCurrentWithViewDetachedAnimateCameraNotCalled() {
        presenter.setCurrentLocation(currentLocation);
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.handleLocationUpdate(false, newLocation);

        Mockito.verify(view, Mockito.times(0)).animateCamera(newLocation);
        Mockito.verify(view, Mockito.times(0)).sendAnalytics(newLocation);
        Assert.assertNotEquals(presenter.getCurrentLocation(),newLocation);
    }

// 4    handleLocationUpdate   not interaction mode new location equals to current

    @Test
    public void handleLocationUpdateNotInteractionModeNewLocationEqualsToCurrentWithNonNullViewAnimateCameraNotCalled() {
        presenter.setCurrentLocation(currentLocation);
        presenter.onAttach(view);
        presenter.handleLocationUpdate(false, currentLocation);

        Mockito.verify(view, Mockito.times(0)).animateCamera(currentLocation);
        Mockito.verify(view, Mockito.times(0)).sendAnalytics(currentLocation);

    }

    @Test
    public void handleLocationUpdateNotInteractionModeNewLocationEqualsToCurrentWithNullViewAnimateCameraNotCalled() {
        presenter.setCurrentLocation(currentLocation);
        presenter.onAttach(null);
        presenter.handleLocationUpdate(false, currentLocation);

        Mockito.verify(view, Mockito.times(0)).animateCamera(currentLocation);
        Mockito.verify(view, Mockito.times(0)).sendAnalytics(currentLocation);

    }

    @Test
    public void handleLocationUpdateNotInteractionModeNewLocationEqualsToCurrentWithViewDetachedAnimateCameraNotCalled() {
        presenter.setCurrentLocation(currentLocation);
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.handleLocationUpdate(false, currentLocation);

        Mockito.verify(view, Mockito.times(0)).animateCamera(currentLocation);
        Mockito.verify(view, Mockito.times(0)).sendAnalytics(currentLocation);

    }

// 5    handleLocationUpdate   interaction mode new location not equals to current

    @Test
    public void handleLocationUpdateInteractionModeNewLocationNotEqualsToCurrentWithNonNullViewAnimateCameraNotCalled() {
        presenter.setCurrentLocation(currentLocation);
        presenter.onAttach(view);
        presenter.handleLocationUpdate(true, newLocation);

        Mockito.verify(view, Mockito.times(0)).animateCamera(newLocation);
        Mockito.verify(view, Mockito.times(0)).sendAnalytics(newLocation);
        Assert.assertEquals(presenter.getCurrentLocation(),newLocation);
    }

    @Test
    public void handleLocationUpdateInteractionModeNewLocationNotEqualsToCurrentWithNullViewAnimateCameraNotCalled() {
        presenter.setCurrentLocation(currentLocation);
        presenter.onAttach(null);
        presenter.handleLocationUpdate(true, newLocation);

        Mockito.verify(view, Mockito.times(0)).animateCamera(newLocation);
        Mockito.verify(view, Mockito.times(0)).sendAnalytics(newLocation);
        Assert.assertNotEquals(presenter.getCurrentLocation(),newLocation);

    }

    @Test
    public void handleLocationUpdateInteractionModeNewLocationNotEqualsToCurrentWithViewDetachedAnimateCameraNotCalled() {
        presenter.setCurrentLocation(currentLocation);
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.handleLocationUpdate(true, newLocation);

        Mockito.verify(view, Mockito.times(0)).animateCamera(newLocation);
        Mockito.verify(view, Mockito.times(0)).sendAnalytics(newLocation);
        Assert.assertNotEquals(presenter.getCurrentLocation(),newLocation);
    }

// 6    handleLocationUpdate   interaction mode new location equals to current

    @Test
    public void handleLocationUpdateInteractionModeNewLocationEqualsToCurrentWithNonNullViewAnimateCameraNotCalled() {
        presenter.setCurrentLocation(currentLocation);
        presenter.onAttach(view);
        presenter.handleLocationUpdate(true, currentLocation);

        Mockito.verify(view, Mockito.times(0)).animateCamera(currentLocation);
        Mockito.verify(view, Mockito.times(0)).sendAnalytics(currentLocation);

    }

    @Test
    public void handleLocationUpdateInteractionModeNewLocationEqualsToCurrentWithNullViewAnimateCameraNotCalled() {
        presenter.setCurrentLocation(currentLocation);
        presenter.onAttach(null);
        presenter.handleLocationUpdate(true, currentLocation);

        Mockito.verify(view, Mockito.times(0)).animateCamera(currentLocation);
        Mockito.verify(view, Mockito.times(0)).sendAnalytics(currentLocation);

    }

    @Test
    public void handleLocationUpdateInteractionModeNewLocationEqualsToCurrentWithViewDetachedAnimateCameraNotCalled() {
        presenter.setCurrentLocation(currentLocation);
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.handleLocationUpdate(true, currentLocation);

        Mockito.verify(view, Mockito.times(0)).animateCamera(currentLocation);
        Mockito.verify(view, Mockito.times(0)).sendAnalytics(currentLocation);

    }

// 7    checkEnableGpsLocation  location available

    @Test
    public void checkEnableGpsLocationLocationAvailableWithNonNullViewShowLocationAlertDialogNotCalled() {
        presenter.onAttach(view);
        presenter.checkEnableGpsLocation();

        Mockito.verify(view, Mockito.times(0)).showLocationAlertDialog();
    }

    @Test
    public void checkEnableGpsLocationLocationAvailableWithNullViewShowLocationAlertDialogNotCalled() {
        presenter.onAttach(null);
        presenter.checkEnableGpsLocation();

        Mockito.verify(view, Mockito.times(0)).showLocationAlertDialog();
    }

    @Test
    public void checkEnableGpsLocationLocationAvailableWithViewDetachedShowLocationAlertDialogNotCalled() {
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.checkEnableGpsLocation();

        Mockito.verify(view, Mockito.times(0)).showLocationAlertDialog();
    }

// 8    checkEnableGpsLocation  location not available

    @Test
    public void checkEnableGpsLocationLocationNotAvailableWithNonNullViewShowLocationAlertDialogCalled() {
        Mockito.when(view.isLocationAvailable()).thenReturn(false);
        presenter.onAttach(view);
        presenter.checkEnableGpsLocation();

        Mockito.verify(view, Mockito.times(1)).showLocationAlertDialog();
    }

    @Test
    public void checkEnableGpsLocationLocationNotAvailableWithNullViewShowLocationAlertDialogNotCalled() {
        Mockito.when(view.isLocationAvailable()).thenReturn(false);
        presenter.onAttach(null);
        presenter.checkEnableGpsLocation();

        Mockito.verify(view, Mockito.times(0)).showLocationAlertDialog();
    }

    @Test
    public void checkEnableGpsLocationLocationNotAvailableWithViewDetachedShowLocationAlertDialogNotCalled() {
        Mockito.when(view.isLocationAvailable()).thenReturn(false);
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.checkEnableGpsLocation();

        Mockito.verify(view, Mockito.times(0)).showLocationAlertDialog();
    }

// 9    openSettings

    @Test
    public void openSettingsWithNonNullViewOpenSettingsCalled() {
        presenter.onAttach(view);
        presenter.openSettings();

        Mockito.verify(view, Mockito.times(1)).openSettings();
    }

    @Test
    public void openSettingsWithNullViewOpenSettingsNotCalled() {
        presenter.onAttach(null);
        presenter.openSettings();

        Mockito.verify(view, Mockito.times(0)).openSettings();
    }

    @Test
    public void openSettingsWithViewDetachedOpenSettingsNotCalled() {
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.openSettings();

        Mockito.verify(view, Mockito.times(0)).openSettings();
    }

// 10   exit

    @Test
    public void exitWithNonNullViewExitCalled() {
        presenter.onAttach(view);
        presenter.exit();

        Mockito.verify(view, Mockito.times(1)).exit();
    }

    @Test
    public void exitWithNullViewExitNotCalled() {
        presenter.onAttach(null);
        presenter.exit();

        Mockito.verify(view, Mockito.times(0)).exit();
    }

    @Test
    public void exitWithViewDetachedExitCalled() {
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.exit();

        Mockito.verify(view, Mockito.times(0)).exit();
    }

    @After
    public void tearDown() throws Exception {

    }
}
