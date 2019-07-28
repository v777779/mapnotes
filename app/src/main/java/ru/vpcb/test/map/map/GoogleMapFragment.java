package ru.vpcb.test.map.map;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.vpcb.test.map.MainApp;
import ru.vpcb.test.map.R;
import ru.vpcb.test.map.activity.IComponent;
import ru.vpcb.test.map.activity.home.HomeActivity;
import ru.vpcb.test.map.data.provider.LocationProvider;
import ru.vpcb.test.map.executors.IListener;
import ru.vpcb.test.map.ext.PermissionExt;
import ru.vpcb.test.map.model.Location;
import ru.vpcb.test.map.model.Note;

public class GoogleMapFragment extends SupportMapFragment implements MapView, OnMapReadyCallback, IComponent {
    private static GoogleMapFragment instance;

    public static GoogleMapFragment getInstance() {
        if (instance == null) {
            synchronized (GoogleMapFragment.class) {
                if (instance == null)
                    instance = new GoogleMapFragment();
            }
        }
        return instance;
    }

    @Inject
    MapMvpPresenter presenter;
    @Inject
    LocationProvider locationProvider;

    private AppCompatActivity activity;
    private GoogleMap map;
    private List<MarkerOptions> markers;
    private boolean isInteractionMode;
    private BroadcastReceiver displayOnMapBroadcastListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (AppCompatActivity) activity;
        setupComponent();

        this.map = null;
        this.markers = new ArrayList<>();
        this.isInteractionMode = false;
        this.displayOnMapBroadcastListener = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Note note = intent.getParcelableExtra(HomeActivity.EXTRA_NOTE);
                presenter.handleMapNote(note);
            }
        };


    }

    @Override
    public void onResume() {
        super.onResume();
        Context context = getContext();
        if (context == null) return;
        LocalBroadcastManager.getInstance(context)
                .registerReceiver(displayOnMapBroadcastListener,
                        new IntentFilter(HomeActivity.DISPLAY_LOCATION));
    }

    @Override
    public void onPause() {
        super.onPause();
        Context context = getContext();
        if (context == null) return;
        LocalBroadcastManager.getInstance(context)
                .unregisterReceiver(displayOnMapBroadcastListener);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onAttach(this);
        locationProvider.startLocationUpdates();
        locationProvider.addUpdatableLocationListener(new IListener<Location>() {
            @Override
            public void invoke(Location location) {
                presenter.handleLocationUpdate(isInteractionMode(), location);
            }
        });
        getMapAsync(this);
    }

    @Override
    public void onStop() {
//        releaseProperties(Properties.FRAGMENT_CONTEXT)
        locationProvider.stopLocationUpdates();
        presenter.onDetach();
        super.onStop();
    }

// support view

    @Override
    public void animateCamera(@NonNull Location currentLocation) {
        if (map == null) return;
        map.animateCamera(CameraUpdateFactory.newLatLng(
                new LatLng(currentLocation.getLatitude(),
                        currentLocation.getLongitude()))
        );
    }

    @Override
    public void displayNoteOnMap(Note note) {
        if (map == null || markers == null || note == null) return;
        LatLng notePos = new LatLng(note.getLatitude(), note.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions()
                .position(notePos)
                .title(note.getText());
        map.addMarker(markerOptions).showInfoWindow();
        map.animateCamera(CameraUpdateFactory.newLatLng(notePos));
        markers.add(markerOptions);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        updateInitLocation(map);
        Activity activity = getActivity();
        if (activity == null) return;
        if (!locationProvider.isLocationAvailable()) {
            AlertDialog dialog = new AlertDialog.Builder(activity)
                    .setMessage(R.string.use_location_message)
                    .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            activity.finish();
                        }
                    }).create();

            dialog.show();

        }
        if (PermissionExt.checkLocationPermission(activity)) {
            map.setMyLocationEnabled(true);
            map.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    setInteractionMode(false);
                    return true;
                }
            });

            map.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
                @Override
                public void onCameraMoveStarted(int reason) {
                    if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
                        setInteractionMode(true);
                    }
                }
            });
        }
    }


    // methods
// TODO Check Map Not Showed at First Location
    private void updateInitLocation(GoogleMap map) {
        if (map == null) return;
        float defaultZoom = 6.0f;
        LatLng initialLoc = map.getCameraPosition().target;
        CameraUpdate location = CameraUpdateFactory
                .newLatLngZoom(initialLoc, defaultZoom);
        map.moveCamera(location);
    }

    void clearAllMarkers() {
        if (map == null || markers == null) return;
        map.clear();
        markers.clear();
    }

    public boolean isInteractionMode() {
        return isInteractionMode;
    }

    public void setInteractionMode(boolean mode) {
        isInteractionMode = mode;
        presenter.handleInteractionMode(isInteractionMode);
    }

    public List<MarkerOptions> getMarkers() {
        return markers;
    }

    @Override
    public void setupComponent() {
        MainApp.plus(activity)
                .inject(this);
    }
}
