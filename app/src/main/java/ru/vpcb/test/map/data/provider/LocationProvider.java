package ru.vpcb.test.map.data.provider;

public interface LocationProvider {
    void startLocationUpdates();

    void addUpdatableLocationListener(LocationListener listener);

    void addSingleLocationListener(LocationListener listener);

    void stopLocationUpdates();

    boolean isLocationAvailable();
}
