package ru.vpcb.map.notes.data.provider;

import ru.vpcb.map.notes.executors.IListener;
import ru.vpcb.map.notes.model.Location;

public interface LocationProvider {
    void startLocationUpdates();

    void addUpdatableLocationListener(IListener<Location> listener);

    void addSingleLocationListener(IListener<Location> listener);

    void stopLocationUpdates();

}
