package ru.vpcb.test.map.map;

import android.support.v4.app.Fragment;

public interface MapFragment {

    Fragment fragment();

    boolean isInteractionMode();

    void setInteractionMode(boolean isInteractionMode);

    public boolean hasMarkersOnMap();

    void clearAllMarkers();
}
