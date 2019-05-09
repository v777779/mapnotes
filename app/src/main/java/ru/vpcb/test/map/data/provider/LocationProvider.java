package ru.vpcb.test.map.data.provider;

import android.location.LocationListener;

import java.util.Locale;

public interface LocationProvider {
    void startLocationUpdates();

    void addUpdatableLocationListener(LocationListener listener);

    void addSingleLocationListener(LocationListener listener);

    void stopLocationUpdates();

    boolean isLocationAvailable();
}
