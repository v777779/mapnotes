package ru.vpcb.map.notes.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.SupportMapFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.vpcb.map.notes.R
import ru.vpcb.map.notes.databinding.FragmentHomeBinding
import ru.vpcb.map.notes.model.MResult
import ru.vpcb.map.notes.ui.MainViewModel
import ru.vpcb.map.notes.ui.auth.AuthViewModel
import ru.vpcb.map.notes.utils.collects
import ru.vpcb.map.notes.utils.navigated
import ru.vpcb.map.notes.utils.showError
import ru.vpcb.map.notes.utils.toToast
import java.util.Locale

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel by activityViewModels<MainViewModel>()
    private val authViewModel by activityViewModels<AuthViewModel>()
    private val mapViewModel by viewModels<MapViewModel>()
    private val locationViewModel by activityViewModels<LocationViewModel>()
    private val homeViewModel by viewModels<HomeViewModel>()

    // locations
    private val requestMultiplePermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            locationViewModel.processingLocationPermissions(this@HomeFragment, it)
        }
    }

    private val requestLocationSettings = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            delay(500)  // to take effect
            locationViewModel.checkLocationPermissions(activity, authViewModel)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.register.collects(viewLifecycleOwner) {
            mainViewModel.progress(false)
            when (it) {
                is MResult.Loading -> mainViewModel.progress(true)
                is MResult.Success -> {
//                    mapViewModel.markers(it.data)
//                    mainViewModel.badge(it.data.size)
                }

                is MResult.Error -> showError(context, it)
                else -> {}
            }
        }
        mainViewModel.notes.collects(viewLifecycleOwner){
            if(it is MResult.Success){
                mainViewModel.badge(it.data.size)
                mapViewModel.markers(it.data)
                mainViewModel.badge(it.data.size)
            }
        }
        mainViewModel.camera.collects(viewLifecycleOwner){
            mapViewModel.moveCamera(it.latitude,it.longitude)
        }

        mapViewModel.note.collects(viewLifecycleOwner) {
            if (it.show == true) homeViewModel.showCard(binding.card, it)
            else homeViewModel.hideCard(binding.card)
        }

        mapViewModel.message.collects(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
        /**
         * fine location or coarse location
         */
        locationViewModel.locationState.collects(viewLifecycleOwner) {
            if (it) mapViewModel.map?.isMyLocationEnabled = true
        }
        locationViewModel.location.collects(viewLifecycleOwner) {
            it?.let { loc ->
                "Location: updates ${
                    String.format(Locale.getDefault(), "%.6f, %.6f", loc.lat, loc.lon)
                }".toToast(context)
            }

        }

        binding.card.chipScreen.setOnClickListener {
            locationViewModel.startLocationUpdates(locationViewModel.permissions)
        }
        binding.card.chipEdit.setOnClickListener {
            locationViewModel.stopLocationUpdates()
        }

        authViewModel.userExpired()
        if (!authViewModel.logged()) navigated(R.id.fragment_login)
        else {
            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment?.getMapAsync(mapViewModel.callback(locationViewModel))
        }

        locationViewModel.init(requestMultiplePermissions, requestLocationSettings)

    }

    override fun onStart() {
        super.onStart()
        locationViewModel.checkLocationPermissions(activity, authViewModel)
        homeViewModel.readNotes(authViewModel)
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}