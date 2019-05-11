package ru.vpcb.test.map.data.provider;

import ru.vpcb.test.map.executors.IListener;
import ru.vpcb.test.map.model.Location;

public interface LocationProvider {
    void startLocationUpdates();

    void addUpdatableLocationListener(IListener<Location> listener);

    void addSingleLocationListener(IListener<Location> listener);

    void stopLocationUpdates();

    boolean isLocationAvailable();
}
