package ru.vpcb.map.notes.ui.home

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.vpcb.map.notes.core.Core
import ru.vpcb.map.notes.location.LocationClient
import ru.vpcb.map.notes.location.Located
import ru.vpcb.map.notes.model.Location
import ru.vpcb.map.notes.ui.auth.Signed
import ru.vpcb.map.notes.utils.CryptoPrefs
import ru.vpcb.map.notes.utils.ERROR_BACKGROUND_PERMISSIONS
import ru.vpcb.map.notes.utils.ERROR_FINE_PERMISSIONS
import ru.vpcb.map.notes.utils.ERROR_LOCATION_SETTINGS
import ru.vpcb.map.notes.utils.LOCATION_INTERVAL
import ru.vpcb.map.notes.utils.navigated
import ru.vpcb.map.notes.utils.popBackStacked
import ru.vpcb.map.notes.utils.toSnackBar
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationClient: LocationClient,
) : ViewModel(), Located {

    private val _locationState = MutableSharedFlow<Boolean>()
    val locationState = _locationState.asSharedFlow()

    val location = locationClient.location.map {
        currentLocation = Location(it.latitude, it.longitude)
        currentLocation
    }

    private var requestPermissions: ActivityResultLauncher<Array<String>>? = null
    private var requestSettings: ActivityResultLauncher<IntentSenderRequest>? = null

   override var currentLocation: Location? = null
        private set

    var permissions = false
        private set


    // methods
    // location
    fun stopLocationUpdates() {
        if (locationClient.started) locationClient.stopLocationUpdates()
    }

    fun startLocationUpdates(value: Boolean) {
        if (!locationClient.started) locationClient.startLocationUpdates(value)
    }


    private fun setLocationState(value: Boolean) {
        viewModelScope.launch {
            _locationState.emit(value)
        }
    }

    // permission
    fun init(
        requestPermissions: ActivityResultLauncher<Array<String>>,
        requestSettings: ActivityResultLauncher<IntentSenderRequest>
    ) {
        this.requestPermissions = requestPermissions
        this.requestSettings = requestSettings
    }


    fun reloadFragment(fragment: Fragment, id: Int) {
        fragment.popBackStacked(id, true)
        fragment.navigated(id)
    }

    fun processingLocationPermissions(fragment: Fragment?, result: Map<String, Boolean>) {
        val activity = fragment?.activity ?: return
        val requestPermissions = requestPermissions ?: return
        if (result.isEmpty()) return

        when (Core.permissionState()) {
            CryptoPrefs.PermissionState.FINE_RATIONALE -> {
                val granted = result[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
//            val fine = shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
                if (!granted) {
                    activity.toSnackBar(ERROR_FINE_PERMISSIONS) {
                        requestLocationPermissions(fragment.activity)
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        Core.permissionState(CryptoPrefs.PermissionState.FINE_EXPIRED)
                    } else {
                        Core.permissionState(CryptoPrefs.PermissionState.LOCATION_RATIONALE)
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        Core.permissionState(CryptoPrefs.PermissionState.BACK_RATIONALE)
                    } else {
                        Core.permissionState(CryptoPrefs.PermissionState.LOCATION_RATIONALE)
                    }
                    requestLocationPermissions(fragment.activity)
                }
            }

            CryptoPrefs.PermissionState.FINE_EXPIRED -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    Core.permissionState(CryptoPrefs.PermissionState.BACK_RATIONALE)
                    requestLocationPermissions(fragment.activity)
                }

            }

            CryptoPrefs.PermissionState.BACK_RATIONALE -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    val granted = result[Manifest.permission.ACCESS_BACKGROUND_LOCATION] ?: false
//            val back = shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                    if (!granted) {
                        activity.toSnackBar(ERROR_BACKGROUND_PERMISSIONS) {
                            requestLocationPermissions(fragment.activity)
                        }
                        Core.permissionState(CryptoPrefs.PermissionState.BACK_EXPIRED)
                    } else {
                        Core.permissionState(CryptoPrefs.PermissionState.LOCATION_RATIONALE)
                        requestLocationPermissions(fragment.activity)
                    }
                }
            }

            CryptoPrefs.PermissionState.BACK_EXPIRED -> {
                Core.permissionState(CryptoPrefs.PermissionState.LOCATION_RATIONALE)
                requestLocationPermissions(fragment.activity)
            }

            else -> {}
        }
    }

    private fun checkPermission(context: Context, p: String) =
        ActivityCompat.checkSelfPermission(context, p) == PackageManager.PERMISSION_GRANTED

    private fun checkPermissions(context: Context?, update: Boolean = false): Boolean {
        context ?: return false
        val fine = checkPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarse = checkPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
        val background = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) checkPermission(
            context,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        ) else true

        return if (update) (fine || coarse)
        else fine && coarse && background
    }

    private fun actionSettings(context: Context, ex: Exception) {
        val requestSettings = requestSettings ?: return
        if (ex is ResolvableApiException) {
            val ib = IntentSenderRequest.Builder(ex.resolution).build()
            requestSettings.launch(ib)
        } else {
            context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }
    }

    /**
     * request settings mandatory for map current location
     */
    private fun requestLocationSettings(activity: Activity, action: () -> Unit) {
        val request =
            LocationRequest.Builder(Priority.PRIORITY_BALANCED_POWER_ACCURACY, LOCATION_INTERVAL).build()
        val settings =
            LocationSettingsRequest.Builder().addLocationRequest(request).setAlwaysShow(false).build()

        LocationServices.getSettingsClient(activity).checkLocationSettings(settings)
            .addOnSuccessListener {
                action()
                Core.permissionState(CryptoPrefs.PermissionState.LOCATION_FINAL)
            }.addOnFailureListener { ex ->
                when (Core.permissionState()) {
                    CryptoPrefs.PermissionState.LOCATION_RATIONALE -> {
                        actionSettings(activity, ex)
                        Core.permissionState(CryptoPrefs.PermissionState.LOCATION_EXPIRED)
                    }

                    CryptoPrefs.PermissionState.LOCATION_EXPIRED -> {
                        activity.toSnackBar(ERROR_LOCATION_SETTINGS) {
                            actionSettings(activity, ex)
                        }
                        Core.permissionState(CryptoPrefs.PermissionState.LOCATION_FINAL)
                    }

                    else -> {
                        action()
                        Core.permissionState(CryptoPrefs.PermissionState.LOCATION_FINAL)
                    }
                }
            }
    }

    private fun requestLocationPermissions(activity: Activity?) {
        activity ?: return
        val requestPermissions = requestPermissions ?: return
        when (Core.permissionState()) {
            CryptoPrefs.PermissionState.FINE_RATIONALE,
            CryptoPrefs.PermissionState.FINE_EXPIRED -> {
                requestPermissions.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
            }

            CryptoPrefs.PermissionState.BACK_RATIONALE,
            CryptoPrefs.PermissionState.BACK_EXPIRED -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                    requestPermissions.launch(arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION))
            }

            else -> {
                requestLocationSettings(activity) {
                    permissions = checkPermissions(activity, true)
                    setLocationState(permissions)
                    startLocationUpdates(permissions)
                }
            }
        }
    }

    fun checkLocationPermissions(activity: Activity?, signed: Signed) {
        permissions = checkPermissions(activity)
        activity ?: return
        if (!signed.logged()) return
        if (GoogleApiAvailability.getInstance()
                .isGooglePlayServicesAvailable(activity) != ConnectionResult.SUCCESS
        ) {
            activity.finish()
            return
        }

        requestLocationPermissions(activity)
    }

}