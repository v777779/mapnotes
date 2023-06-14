package ru.vpcb.map.notes.location

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.location.LocationResult
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber


class LocationReceiver : BroadcastReceiver() {
    private val scope = CoroutineScope(SupervisorJob())

    private var locationClient: LocationClient? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        context ?: return
        val hiltEntryPoint = EntryPointAccessors.fromApplication(context, LocationProvider::class.java)

        if (intent?.action == LocationClient.ACTION_LOCATION_UPDATES) {
            val pending = goAsync()
            locationClient = hiltEntryPoint.provideLocationClient()

            scope.launch {
                try {
                    val result: LocationResult? = LocationResult.extractResult(intent)
                    result?.locations?.forEach {
                        locationClient?.location(it)
                        Timber.d("Location: ${it.latitude}, ${it.longitude}")
                    }
                } finally {
                    pending.finish()
                }
            }

        }
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface LocationProvider {
        fun provideLocationClient(): LocationClient

        fun provideLocationHolder():LocationHolder
    }
}