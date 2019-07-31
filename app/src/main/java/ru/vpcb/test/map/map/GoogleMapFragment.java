package ru.vpcb.test.map.map;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
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
        if (activity == null) {
            return;
        }
        LocalBroadcastManager.getInstance(activity)
                .registerReceiver(displayOnMapBroadcastListener,
                        new IntentFilter(HomeActivity.DISPLAY_LOCATION));
    }

    @Override
    public void onPause() {
        super.onPause();
        if (activity == null) {
            return;
        }
        LocalBroadcastManager.getInstance(activity)
                .unregisterReceiver(displayOnMapBroadcastListener);
    }

    // TODO Check this Properties.FRAGMENT_CONTEXT setup
    @Override
    public void onStart() {
        super.onStart();
        if (presenter != null) {
            presenter.onAttach(this);
        }
        if (locationProvider != null) {
            locationProvider.startLocationUpdates();
            locationProvider.addUpdatableLocationListener(new IListener<Location>() {
                @Override
                public void invoke(Location location) {
                    presenter.handleLocationUpdate(isInteractionMode(), location);
                }
            });
        }
        getMapAsync(this);
    }

// TODO Check this Properties.FRAGMENT_CONTEXT release
    @Override
    public void onStop() {
//        releaseProperties(Properties.FRAGMENT_CONTEXT)
        if (locationProvider != null) {
            locationProvider.stopLocationUpdates();
        }
        if (presenter != null) {
            presenter.onDetach();
        }
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
        if (presenter != null) {
            presenter.checkEnableGpsLocation();
        }
        if (map == null) {
            return;
        }
        if (PermissionExt.checkLocationPermission(activity)) {
            map.setMyLocationEnabled(true);
            map.setOnMyLocationButtonClickListener(() -> {
                setInteractionMode(false);
                return true;
            });

            map.setOnCameraMoveStartedListener(reason -> {
                if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
                    setInteractionMode(true);
                }
            });
        }
    }

    @Override
    public boolean isLocationAvailable() {
        boolean isGpsEnabled = false;
        boolean isNetworkEnabled = false;

        if (activity == null) {
            return false;
        }

        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) {
            return false;
        }

        try {
            isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        } catch (Exception e) {
            //
        }
        return isGpsEnabled || isNetworkEnabled;

    }

    public void showLocationAlertDialog() {
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setMessage(R.string.use_location_message)
                .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.openSettings();
                    }
                })
                .setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.exit();
                    }
                }).create();

        dialog.show();
    }

    public void openSettings() {
        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }

    public void exit() {
        if (activity == null) {
            return;
        }
        activity.finish();
    }

    @Override
    public void setupComponent() {
        if (activity == null) {
            return;
        }
        MainApp.plus(activity).inject(this);
    }

// methods

    void clearAllMarkers() {
        if (map == null || markers == null) return;
        map.clear();
        markers.clear();
    }

    List<MarkerOptions> getMarkers() {
        return markers;
    }

    private void updateInitLocation(GoogleMap map) {
        if (map == null) return;
        float defaultZoom = 12.0f;
        LatLng initialLoc = map.getCameraPosition().target;
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(initialLoc, defaultZoom);
        map.moveCamera(location);
    }


    private boolean isInteractionMode() {
        return isInteractionMode;
    }

    private void setInteractionMode(boolean mode) {
        isInteractionMode = mode;
        presenter.handleInteractionMode(isInteractionMode);
    }


}
