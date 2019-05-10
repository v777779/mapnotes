package ru.vpcb.test.map.map;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import ru.vpcb.test.map.R;

public class GeneralMapFragment2 extends Fragment {


    private View mRootView;
    private SupportMapFragment mMapFragment;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_home,container,false);

        if(mMapFragment == null) mMapFragment = new GoogleMapFragment();
        mMapFragment.getMapAsync((OnMapReadyCallback)mMapFragment);

        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_home_map,mMapFragment)
                .commit();

        return mRootView;
    }
}
