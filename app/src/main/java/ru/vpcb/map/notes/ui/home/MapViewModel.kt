package ru.vpcb.map.notes.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.vpcb.map.notes.location.Located
import ru.vpcb.map.notes.model.Note
import ru.vpcb.map.notes.utils.MAP_LAT_DEFAULT
import ru.vpcb.map.notes.utils.MAP_LON_DEFAULT
import ru.vpcb.map.notes.utils.MAP_ZOOM_DEFAULT
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor() : ViewModel() {

    private val _note = MutableSharedFlow<Note>()
    val note = _note.asSharedFlow()

    private val _message = MutableSharedFlow<String>()
    val message = _message.asSharedFlow()

    private val markers = mutableMapOf<Marker, Note>()

    var map: GoogleMap? = null


    var callback: OnMapReadyCallback? = null

    private fun position(latLng: LatLng?) = latLng?.let {
        map?.projection?.toScreenLocation(it)
    }

    // methods
    fun moveCamera(latLng: LatLng? = null, zoom: Float = MAP_ZOOM_DEFAULT) {
        val pos = latLng ?: LatLng(MAP_LAT_DEFAULT, MAP_LON_DEFAULT)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, zoom))
    }

    fun delete(pos: LatLng?): Boolean {
        pos ?: return false
        position(pos)?.let { screen ->
            markers.keys.forEach { marker ->
                position(marker.position)?.let { point ->
                    if (point.x == screen.x && point.y == screen.y) {
                        marker.remove()
                        return true
                    }
                }
            }
        }
        return false
    }

    fun markers(notes: List<Note>?) {
        if (notes.isNullOrEmpty()) return
        val map = map ?: return
        map.clear()
        markers.clear()
        notes.filter { it.lat != null && it.lon != null }.map { note ->
            val options = MarkerOptions().apply {
                title("Note: ${note.title}")
                position(LatLng(note.lat!!, note.lon!!))
                this.snippet(note.key)
            }

            val marker = map.addMarker(options) ?: return
            markers[marker] = note
        }
    }

    fun callback(provider: Located) = callback ?: OnMapReadyCallback { map ->
        this.map = map

        map.setOnMyLocationClickListener {
            viewModelScope.launch {
                _message.emit("Maps: location $it")
            }
        }

        map.setOnMyLocationButtonClickListener button@{
            val lat = provider.currentLocation?.lat
            val lon = provider.currentLocation?.lon
            if (lat == null || lon == null) return@button false
            map.moveCamera(CameraUpdateFactory.newLatLng(LatLng(lat, lon)))
            true
        }

        map.setOnMarkerClickListener {
            val note = markers[it]
            if (note == null) false
            else {
                viewModelScope.launch {
                    _note.emit(note.apply {
                        screen = position(it.position)
                        show = true
                    })
                }
                true
            }
        }

        map.setOnMapLongClickListener {
            delete(it)

//            mapViewModel.currentLocation = Location(
//                lat = it.latitude,
//                lon = it.longitude,
//            )
//            map.addMarker(MarkerOptions().position(it).title("Marker for store"))
        }

        /**
         *  POI    Point Of Business  создать маркер и показать данные университета
         */

        /**
         *  POI    Point Of Business  создать маркер и показать данные университета
         */
        map.setOnPoiClickListener { poi ->
            val options = MarkerOptions().position(poi.latLng).title(poi.name)
            val poiMarker = map.addMarker(options)
            poiMarker?.showInfoWindow()
        }

        map.setOnMapClickListener {
            viewModelScope.launch {
                _note.emit(Note())
            }
        }

        moveCamera()

    }.also {
        callback = it
    }


}