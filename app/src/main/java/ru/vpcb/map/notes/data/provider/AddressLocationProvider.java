package ru.vpcb.map.notes.data.provider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.LocationManager;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import ru.vpcb.map.notes.executors.IConsumer;
import ru.vpcb.map.notes.ext.PermissionExt;
import ru.vpcb.map.notes.model.Location;

public class AddressLocationProvider implements LocationProvider {
    public static final long REQUEST_INTERVAL = 3000;

    private Context context;
    private long requestInterval;

    private IConsumer<Location> updatableListener;
    private IConsumer<Location> singleListener;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;


    public AddressLocationProvider(Context context, long requestInterval) {
        this.context = context;
        this.requestInterval = requestInterval;

        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        this.locationRequest = LocationRequest.create()
                .setInterval(this.requestInterval)
                .setFastestInterval(this.requestInterval);  // to real delay for requestInterval
        this.locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) return;
                if (updatableListener != null)
                    updatableListener.accept(new Location(locationResult
                            .getLastLocation().getLatitude(),
                            locationResult.getLastLocation().getLongitude()));

                if (singleListener != null) {
                    singleListener.accept(new Location(
                            locationResult.getLastLocation().getLatitude(),
                            locationResult.getLastLocation().getLongitude()));
                    singleListener = null;
                }
            }
        };
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
    public void addUpdatableLocationListener(IConsumer<Location> listener) {
        this.updatableListener = listener;
    }

    @Override
    public void addSingleLocationListener(IConsumer<Location> listener) {
        this.singleListener = listener;
    }

    @Override
    public void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    @Override
    public boolean isLocationAvailable() {
        boolean isGpsEnabled = false;
        boolean isNetworkEnabled = false;

        if (context == null) {
            return false;
        }

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
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
                    updatableListener.accept(new Location(task.getResult().getLatitude(),
                            task.getResult().getLongitude()));
                    if (singleListener != null) {
                        singleListener.accept(new Location(task.getResult().getLatitude(),
                                task.getResult().getLongitude()));
                        singleListener = null;
                    }
                }
            }
        });

    }
}
