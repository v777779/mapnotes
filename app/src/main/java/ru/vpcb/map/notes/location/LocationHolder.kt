package ru.vpcb.map.notes.location

import android.location.Location
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock


class LocationHolder {

    private var mutex = Mutex()
    private val lock = Any()

    private var currentLocation: Location? = null

    suspend fun setLocation(location: Location) {
        mutex.withLock {
            currentLocation = location
        }
    }

    suspend fun getLocation(): Location? {
        return mutex.withLock {
            currentLocation
        }
    }

}