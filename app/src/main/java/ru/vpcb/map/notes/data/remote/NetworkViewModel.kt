package ru.vpcb.map.notes.data.remote

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NetworkViewModel @Inject constructor() : ViewModel(), Stated {

    private val _state = MutableStateFlow<State?>(null)
    val state = _state.asStateFlow()

    private val _network = MutableStateFlow<Network?>(null)
    val network = _network.asStateFlow()

    private var connected = false

    private val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            _network.value = network
            connected = false
        }

        override fun onCapabilitiesChanged(net: Network, caps: NetworkCapabilities) {
            _state.value = State(
                caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET),
                caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
            )
            _network.value = net
            connected = caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }

        override fun onLost(network: Network) {
            _state.value = State(false, false)
            _network.value = null
            connected = false
        }
    }

    override fun isNotMetered(context: Context?): Boolean {
        val manager = context?.getSystemService(ConnectivityManager::class.java)
        return (manager?.activeNetwork?.let {
            manager.getNetworkCapabilities(it)
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
        } ?: false)
    }

    override fun isConnected(context: Context?): Boolean {
        val manager = context?.getSystemService(ConnectivityManager::class.java)
        return manager?.activeNetwork?.let {
            manager.getNetworkCapabilities(it)
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } ?: false
    }

    override fun isConnected() = connected
    fun unregister(context: Context) {
        try {
            val manager = context.getSystemService(ConnectivityManager::class.java)
            manager.unregisterNetworkCallback(callback)
        } catch (e: Exception) {
            Timber.d(e)
        }
    }

    fun register(context: Context) {
        try {
            val manager = context.getSystemService(ConnectivityManager::class.java)
            manager.registerDefaultNetworkCallback(callback)
        } catch (e: Exception) {
            Timber.d(e)
        }
    }

    data class State(val connected: Boolean, val metered: Boolean)
}