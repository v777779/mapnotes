package ru.vpcb.test.map.data.provider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.LocationManager;
import android.support.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import ru.vpcb.test.map.executors.IListener;
import ru.vpcb.test.map.ext.PermissionExt;
import ru.vpcb.test.map.model.Location;

public class AddressLocationProvider implements LocationProvider {
    private Context context;
    private long requestInterval;

    private IListener updatableListener;
    private IListener singleListener;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;


    public AddressLocationProvider(Context context, long requestInterval) {
        this.context = context;
        this.requestInterval = requestInterval;

        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        this.locationRequest = LocationRequest.create().setInterval(this.requestInterval);
        this.locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) return;
                if (updatableListener != null)
                    updatableListener.invoke(new Location(locationResult
                            .getLastLocation().getLatitude(),
                            locationResult.getLastLocation().getLongitude()));

                if (singleListener != null) {
                    singleListener.invoke(new Location(
                            locationResult.getLastLocation().getLatitude(),
                            locationResult.getLastLocation().getLongitude()));
                    singleListener = null;
                }
            }
        };
    }

    public AddressLocationProvider(Context context) {
        this(context, 3_000);
    }

    @Override
    public void startLocationUpdates() {
        if (PermissionExt.checkLocationPermission(context)) {
            requestLocationUpdates();
        } else {
            useLastLocation();
        }
    }

    @Override
    public void addUpdatableLocationListener(IListener listener) {
        this.updatableListener = listener;
    }

    @Override
    public void addSingleLocationListener(IListener listener) {
        this.singleListener = listener;
    }

    @Override
    public void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    @Override
    public boolean isLocationAvailable() {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isGpsEnabled = false;
        try {
            isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        } catch (Exception e) {
            // not needed because GPS disabled
        }
        return isGpsEnabled;
    }

// methods

    @SuppressLint("MissingPermission")
    private void requestLocationUpdates() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    @SuppressLint("MissingPermission")
    private void useLastLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<android.location.Location>() {
            @Override
            public void onComplete(@NonNull Task<android.location.Location> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    updatableListener.invoke(new Location(task.getResult().getLatitude(),
                            task.getResult().getLongitude()));
                    if (singleListener != null) {
                        singleListener.invoke(new Location(task.getResult().getLatitude(),
                                task.getResult().getLongitude()));
                        singleListener = null;
                    }
                }
            }
        });

    }
}
