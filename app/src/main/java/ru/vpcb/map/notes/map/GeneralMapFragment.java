package ru.vpcb.map.notes.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import ru.vpcb.map.notes.R;


public class GeneralMapFragment extends Fragment implements MapFragment {

    private GoogleMapFragment googleMapFragment;
    private boolean isInteractionMode;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        this.googleMapFragment = new GoogleMapFragment();                                   //  googleMapFragmentLazy.getApplication(); // lazy init
        FragmentManager fm = getFragmentManager();
        if (fm != null) {
            fm.beginTransaction()
                    .replace(R.id.map, googleMapFragment)
                    .commit();
        }

        return rootView;
    }

    @Override
    public Fragment fragment() {
        return this;
    }

    @Override
    public boolean isInteractionMode() {
        return isInteractionMode;
    }

    @Override
    public void setInteractionMode(boolean isInteractionMode) {
        this.isInteractionMode = isInteractionMode;
    }

    @Override
    public boolean hasMarkersOnMap() {
        if (googleMapFragment == null || googleMapFragment.getMarkers() == null) return false;
        return !googleMapFragment.getMarkers().isEmpty();
    }

    @Override
    public void clearAllMarkers() {
        if (googleMapFragment == null || googleMapFragment.getMarkers() == null) return;
        googleMapFragment.clearAllMarkers();
    }

// methods


}
