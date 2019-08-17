package ru.vpcb.map.notes.fragments.map;

import androidx.annotation.NonNull;

import ru.vpcb.map.notes.base.MvpPresenter;
import ru.vpcb.map.notes.model.Location;
import ru.vpcb.map.notes.model.Note;

public interface MapMvpPresenter extends MvpPresenter<MapView> {
    void handleInteractionMode(boolean isInteractionMode);

    void handleMapNote(@NonNull Note note);

    void handleLocationUpdate(boolean isInteractionMode, Location newLocation);

    void checkEnableGpsLocation();

    void openSettings();

    void exit();
}
