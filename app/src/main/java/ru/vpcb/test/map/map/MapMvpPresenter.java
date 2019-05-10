package ru.vpcb.test.map.map;

import android.support.annotation.NonNull;

import ru.vpcb.test.map.base.MvpPresenter;
import ru.vpcb.test.map.model.Location;
import ru.vpcb.test.map.model.Note;

public interface MapMvpPresenter extends MvpPresenter<MapView> {
    void handleInteractionMode(boolean isInteractionMode);

    void handleMapNote(@NonNull Note note);

    void handleLocationUpdate(boolean isInteractionMode, Location newLocation);
}
