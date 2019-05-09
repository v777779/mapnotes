package ru.vpcb.test.map.data.provider;

import android.content.Context;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import ru.vpcb.test.map.model.Location;

public class AddressLocationProvider implements LocationProvider {
    private Context context;
    private long requestInterval;

    private LocationListener updatableListener;
    private LocationListener singleListener;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;


    public AddressLocationProvider(Context context, long requestInterval) {
        this.context = context;
        this.requestInterval = requestInterval;
        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        this.locationRequest = LocationRequest.create().setInterval(requestInterval);
        this.locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if(locationResult== null)return;
                if(updatableListener!= null)
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

    }

    @Override
    public void addUpdatableLocationListener(LocationListener listener) {

    }

    @Override
    public void addSingleLocationListener(LocationListener listener) {

    }

    @Override
    public void stopLocationUpdates() {

    }

    @Override
    public boolean isLocationAvailable() {
        return false;
    }
}
