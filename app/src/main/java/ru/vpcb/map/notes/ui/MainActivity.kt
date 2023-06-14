package ru.vpcb.map.notes.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import ru.vpcb.map.notes.R
import ru.vpcb.map.notes.data.remote.NetworkViewModel
import ru.vpcb.map.notes.databinding.ActivityMainBinding
import ru.vpcb.map.notes.ui.auth.AuthViewModel
import ru.vpcb.map.notes.ui.home.LocationViewModel
import ru.vpcb.map.notes.utils.collects

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel by viewModels<MainViewModel>()
    private val networkViewModel by viewModels<NetworkViewModel>()
    private val authViewModel by viewModels<AuthViewModel>()
    private val locationViewModel by viewModels<LocationViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navController = findNavController(R.id.nav_host_fragment_content_main)
        val navControllerBottom = findNavController(R.id.nav_host_fragment_bottom)
        val bh = BottomSheetBehavior.from(binding.content.frameBottom)
        setupWithNavController(binding.content.navView, navController)


        mainViewModel.progress.collects(this) {
            binding.content.progress.isVisible = it
        }
        mainViewModel.bottom.collects(this) {
            bh.state = it
        }
        mainViewModel.badge.collects(this){
            binding.content.navView.getOrCreateBadge(R.id.action_search).apply {
                number = it
                isVisible = true
            }
        }
        mainViewModel.state.collects(this){
            binding.content.navView.selectedItemId = it
        }

        binding.content.navView.setOnItemSelectedListener {
            mainViewModel.navigate(this, it.itemId, authViewModel)
        }

        navController.addOnDestinationChangedListener { _,_,_->     // nav, dest, bundle ->
        }
        /**
         * close all bottom sheet fragments when hidden
         */
        bh.addBottomSheetCallback(
            object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    mainViewModel.navigateBottom(newState, navControllerBottom)
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {

                }
            })

        setupViews()
    }


    override fun onStart() {
        super.onStart()
        networkViewModel.register(this)
//        mainViewModel.bottom()
    }

    override fun onStop() {
        networkViewModel.unregister(this)
        locationViewModel.stopLocationUpdates()
        super.onStop()
    }

    private fun setupViews(){
        mainViewModel.initBadge()
    }


    // test!!!
    fun binding() = binding
}