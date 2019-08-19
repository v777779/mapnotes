package ru.vpcb.map.notes.fragments.map;

import org.junit.After;
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
    @Mock

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
        presenter.updateCurrentLocation(currentLocation);
        presenter.onAttach(view);
        presenter.handleInteractionMode(true);

        Mockito.verify(view, Mockito.times(0)).animateCamera(currentLocation);
        Mockito.verify(view, Mockito.times(0)).sendAnalytics(currentLocation);
    }

    @Test
    public void handleInteractionModeInteractionModeWithNullViewAnimateCameraNotCalled() {
        presenter.updateCurrentLocation(currentLocation);
        presenter.onAttach(null);
        presenter.handleInteractionMode(true);

        Mockito.verify(view, Mockito.times(0)).animateCamera(currentLocation);
        Mockito.verify(view, Mockito.times(0)).sendAnalytics(currentLocation);
    }

    @Test
    public void handleInteractionModeInteractionModeWithViewDetachedAnimateCameraNotCalled() {
        presenter.updateCurrentLocation(currentLocation);
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.handleInteractionMode(true);

        Mockito.verify(view, Mockito.times(0)).animateCamera(currentLocation);
        Mockito.verify(view, Mockito.times(0)).sendAnalytics(currentLocation);
    }

// 1    handleInteractionMode   not interaction mode

    @Test
    public void handleInteractionModeNotInteractionModeWithNonNullViewAnimateCameraCalled() {
        presenter.updateCurrentLocation(currentLocation);
        presenter.onAttach(view);
        presenter.handleInteractionMode(false);

        Mockito.verify(view, Mockito.times(1)).animateCamera(currentLocation);
        Mockito.verify(view, Mockito.times(1)).sendAnalytics(currentLocation);
    }

    @Test
    public void handleInteractionModeNotInteractionModeWithNullViewAnimateCameraNotCalled() {
        presenter.updateCurrentLocation(currentLocation);
        presenter.onAttach(null);
        presenter.handleInteractionMode(false);

        Mockito.verify(view, Mockito.times(0)).animateCamera(currentLocation);
        Mockito.verify(view, Mockito.times(0)).sendAnalytics(currentLocation);
    }

    @Test
    public void handleInteractionModeNotInteractionModeWithViewDetachedAnimateCameraNotCalled() {
        presenter.updateCurrentLocation(currentLocation);
        presenter.onAttach(view);
        presenter.onDetach();
        presenter.handleInteractionMode(false);

        Mockito.verify(view, Mockito.times(0)).animateCamera(currentLocation);
        Mockito.verify(view, Mockito.times(0)).sendAnalytics(currentLocation);
    }



    @After
    public void tearDown() throws Exception {

    }
}
