package ru.vpcb.map.notes.data.provider;

import ru.vpcb.map.notes.executors.IConsumer;
import ru.vpcb.map.notes.model.Location;

public interface LocationProvider {
    void startLocationUpdates();

    void addUpdatableLocationListener(IConsumer<Location> listener);

    void addSingleLocationListener(IConsumer<Location> listener);

    void stopLocationUpdates();

    boolean isLocationAvailable();
}
