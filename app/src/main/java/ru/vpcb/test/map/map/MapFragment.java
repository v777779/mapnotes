package ru.vpcb.test.map.map;


import androidx.fragment.app.Fragment;

public interface MapFragment {

    Fragment fragment();

    boolean isInteractionMode();

    void setInteractionMode(boolean isInteractionMode);

    boolean hasMarkersOnMap();

    void clearAllMarkers();

 }
