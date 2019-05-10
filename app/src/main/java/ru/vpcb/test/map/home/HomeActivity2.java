package ru.vpcb.test.map.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ru.vpcb.test.map.R;
import ru.vpcb.test.map.map.GeneralMapFragment;

public class HomeActivity2 extends AppCompatActivity implements OnMapReadyCallback{
    private static final String DISPLAY_LOCATION = "display_location";
    private static final String EXTRA_NOTE = "note";

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
// Obtain the SupportMapFragment and get notified when the map is ready to be used.
        FragmentManager fm =  getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment)fm.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

// placeholder
        Fragment f = new GeneralMapFragment();
        fm.beginTransaction()
                .add(R.id.fragment_content,f)
                .commit();
// direct
//        SupportMapFragment f = new GoogleMapFragment();
//        f.getMapAsync((OnMapReadyCallback)f);
//        fm.beginTransaction()
//                .add(R.id.fragment_content,f)
//                .commit();



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

}
