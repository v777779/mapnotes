package ru.vpcb.map.notes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import ru.vpcb.map.notes.fragments.map.MapFragment;

public class FakeMapFragment extends Fragment implements MapFragment {

    @Override
    public View  onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public Fragment fragment() {
        return this;
    }

    @Override
    public boolean isInteractionMode() {
        return true;
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
