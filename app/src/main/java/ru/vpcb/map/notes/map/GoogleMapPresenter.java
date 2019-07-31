package ru.vpcb.map.notes.map;

import androidx.annotation.NonNull;

import ru.vpcb.map.notes.base.ScopedPresenter;
import ru.vpcb.map.notes.model.Location;
import ru.vpcb.map.notes.model.Note;

public class GoogleMapPresenter extends ScopedPresenter<MapView> implements MapMvpPresenter {
    private MapView view;
    private Location currentLocation;

    public GoogleMapPresenter() {
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
        if (view == null) return;
        if (!isInteractionMode) {
            view.animateCamera(currentLocation);
        }
    }

    @Override
    public void handleMapNote(@NonNull Note note) {
        if (view == null) return;
        view.displayNoteOnMap(note);
    }

    @Override
    public void handleLocationUpdate(boolean isInteractionMode, Location newLocation) {
        if (view == null) return;
        if (!isInteractionMode && !newLocation.equals(currentLocation)) {
            view.animateCamera(newLocation);
        }
        currentLocation = newLocation;
    }

    @Override
    public void checkEnableGpsLocation() {
        if (view == null) return;
        if (!view.isLocationAvailable()) {
            view.showLocationAlertDialog();
        }
    }

    @Override
    public void openSettings() {
        view.openSettings();
    }

    public void exit() {
        view.exit();
    }
}
