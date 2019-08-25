package ru.vpcb.map.notes.fragments.map;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import ru.vpcb.map.notes.base.ScopedPresenter;
import ru.vpcb.map.notes.data.provider.LocationProvider;
import ru.vpcb.map.notes.model.Location;
import ru.vpcb.map.notes.model.Note;

public class GoogleMapPresenter extends ScopedPresenter<MapView> implements MapMvpPresenter {
    private MapView view;
    private Location currentLocation;
    private LocationProvider locationProvider;

    public GoogleMapPresenter(LocationProvider locationProvider) {
        this.locationProvider = locationProvider;
        this.view = null;
        this.currentLocation = null;

    }

    @Override
    public void onAttach(@NonNull MapView view) {
        super.onAttach(view);
        this.view = view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.view = null;
    }

    @Override
    public void handleInteractionMode(boolean isInteractionMode) {
        if (view == null) {
            return;
        }
        if (!isInteractionMode) {
            view.animateCamera(currentLocation);
            view.sendAnalytics(currentLocation);
        }
    }

    @Override
    public void handleMapNote(@NonNull Note note) {
        if (view == null) {
            return;
        }
        view.displayNoteOnMap(note);
        view.sendAnalytics(note);
    }

    @Override
    public void handleLocationUpdate(boolean isInteractionMode, Location newLocation) {
        if (view == null) {
            return;
        }
        if (!isInteractionMode && !newLocation.equals(currentLocation)) {
            view.animateCamera(newLocation);
            view.sendAnalytics(newLocation);
        }
        currentLocation = newLocation;
    }

    @Override
    public void checkEnableGpsLocation() {
        if (view == null) {
            return;
        }
        if (!locationProvider.isLocationAvailable()) {
            view.showLocationAlertDialog();
        }
    }

    @Override
    public void openSettings() {
        if (view == null) {
            return;
        }
        view.openSettings();
    }

    public void exit() {
        if (view == null) {
            return;
        }
        view.exit();
    }

// testing

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public void setCurrentLocation(Location location){
        this.currentLocation = location;
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public Location getCurrentLocation(){
        return currentLocation;
    }
}
