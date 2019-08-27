package ru.vpcb.map.notes;

import androidx.fragment.app.Fragment;

import ru.vpcb.map.notes.fragments.map.MapFragment;

public class FakeMapFragment implements MapFragment {

    @Override
    public Fragment fragment() {
        return null;
    }

    @Override
    public boolean isInteractionMode() {
        return false;
    }

    @Override
    public void setInteractionMode(boolean isInteractionMode) {

    }

    @Override
    public boolean hasMarkersOnMap() {
        return false;
    }

    @Override
    public void clearAllMarkers() {

    }
}
