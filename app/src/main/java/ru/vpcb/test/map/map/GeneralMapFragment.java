package ru.vpcb.test.map.map;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;

import ru.vpcb.test.map.R;

public class GeneralMapFragment extends Fragment implements MapFragment {

    private GoogleMapFragment googleMapFragment;
    private boolean isInteractionMode;

    public GeneralMapFragment() {

        this.googleMapFragment = new GoogleMapFragment();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        FragmentManager fm = getFragmentManager();
        if (!(fm == null || googleMapFragment == null)) {
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
        googleMapFragment.getMarkers().clear();
    }

// methods


}
