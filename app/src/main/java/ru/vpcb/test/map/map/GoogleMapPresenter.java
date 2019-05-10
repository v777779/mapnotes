package ru.vpcb.test.map.map;

import android.support.annotation.NonNull;

import ru.vpcb.test.map.base.ScopedPresenter;
import ru.vpcb.test.map.model.Location;
import ru.vpcb.test.map.model.Note;

public class GoogleMapPresenter extends ScopedPresenter<MapView> implements MapMvpPresenter {

    @Override
    public void handleInteractionMode(boolean isInteractionMode) {

    }

    @Override
    public void handleMapNote(@NonNull Note note) {

    }

    @Override
    public void handleLocationUpdate(boolean isInteractionMode, Location newLocation) {

    }
}
