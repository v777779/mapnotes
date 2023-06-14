package ru.vpcb.map.notes.location

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.widget.Toast
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ru.vpcb.map.notes.utils.LOCATION_INTERVAL
import timber.log.Timber
import javax.inject.Inject

class LocationClient @Inject constructor(private val context: Context) {

    private var _location = MutableSharedFlow<LatLng>()
    val location = _location.asSharedFlow()

    private val fusedClient = LocationServices.getFusedLocationProviderClient(context)
    private val request =
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, LOCATION_INTERVAL) // interval
            .setMinUpdateIntervalMillis(LOCATION_INTERVAL)     // fastest
            .setMaxUpdateDelayMillis(LOCATION_INTERVAL)        // maximum
            .build()

    private val pending: PendingIntent by lazy {
        val intent = Intent(context, LocationReceiver::class.java).apply {
            action = ACTION_LOCATION_UPDATES
        }
        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        } else PendingIntent.FLAG_UPDATE_CURRENT

        PendingIntent.getBroadcast(context, 0, intent, flags)
    }

    var started:Boolean = false
        private set


    suspend fun location(location: Location){
        _location.emit(LatLng(location.latitude,location.longitude))
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdates(enabled:Boolean){
        if(!enabled) return
        try{
            fusedClient.requestLocationUpdates(request, pending)
                .addOnSuccessListener {
                    Toast.makeText(context, "Location: update started",Toast.LENGTH_SHORT).show()
                    Timber.d("Location: update started")
                    started = true
                }
                .addOnFailureListener{
                    Timber.d("Error: location ${it.message}")
                }

        }catch (e:Exception){
            Timber.d("Error: location ${e.message}")
        }
    }

    fun stopLocationUpdates(){
        try {
            fusedClient.removeLocationUpdates(pending)
                .addOnSuccessListener {
                    Toast.makeText(context, "Location: update stopped", Toast.LENGTH_SHORT).show()
                    Timber.d("Location: update stopped")
                    started = false
                }
                .addOnFailureListener{
                    Timber.d("Error: location ${it.message}")
                }

        }catch (e:Exception){
            Timber.d("Error: location ${e.message}")
        }
    }



    companion object {
        const val ACTION_LOCATION_UPDATES = "ru.vpcb.map.mm.action.ACTION_LOCATION_EVENT"
    }
}