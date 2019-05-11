package ru.vpcb.test.map.data.provider;

import ru.vpcb.test.map.executors.IListener;

public interface LocationProvider {
    void startLocationUpdates();

    void addUpdatableLocationListener(IListener listener);

    void addSingleLocationListener(IListener listener);

    void stopLocationUpdates();

    boolean isLocationAvailable();
}
