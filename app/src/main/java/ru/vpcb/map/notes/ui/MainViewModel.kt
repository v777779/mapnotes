package ru.vpcb.map.notes.ui

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.vpcb.map.notes.R
import ru.vpcb.map.notes.data.local.LocalRepo
import ru.vpcb.map.notes.data.remote.FirebaseRepo
import ru.vpcb.map.notes.model.Note
import ru.vpcb.map.notes.ui.auth.Signed
import ru.vpcb.map.notes.utils.navigated
import ru.vpcb.map.notes.utils.popBackStacked
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val localRepo: LocalRepo,
    private val firebaseRepo: FirebaseRepo
) : ViewModel(), Noted {

    private val _progress = MutableSharedFlow<Boolean>()
    val progress = _progress.asSharedFlow()

    private val _bottom = MutableSharedFlow<Int>(1)
    val bottom = _bottom.asSharedFlow()

    private val _state = MutableSharedFlow<Int>()
    val state = _state.asSharedFlow()

    val notes = firebaseRepo.notes()

    private val _badge = MutableSharedFlow<Int>()
    val badge = _badge.asSharedFlow()

    override var currentNote: Note? = null

    init {
        bottom(BottomSheetBehavior.STATE_HIDDEN)
    }

    fun navigateBottom(state: Int, navBottom: NavController) {
        when (state) {
            BottomSheetBehavior.STATE_HIDDEN -> {
                navBottom.popBackStacked(R.id.fragment_empty, false)
            }

            else -> {}
        }
    }

    fun navigate(dest: Int) {
        viewModelScope.launch {
            _state.emit(dest)
        }
    }

    /**
     * if user token expired makes logout and navigates to login
     */
    fun navigate(activity: Activity?, dest: Int, signed: Signed): Boolean {
        progress(false)
        activity ?: return false
        val navController = activity.findNavController(R.id.nav_host_fragment_content_main)
        val navBottom = activity.findNavController(R.id.nav_host_fragment_bottom)
        signed.userExpired()            // force logout
        val logged = signed.logged()
        return when (dest) {
            R.id.fragment_home -> {
                bottom(BottomSheetBehavior.STATE_HIDDEN)
                navController.popBackStacked(R.id.fragment_home, false)
                if (!logged) navController.navigated(R.id.fragment_login)
                true
            }

            R.id.action_add -> {
                if (logged) {
                    navController.popBackStacked(R.id.fragment_home, false)
                }
                navBottom.navigated(R.id.fragment_add)
                true
            }

            R.id.action_search -> {
                if (logged) {
                    navController.popBackStacked(R.id.fragment_home, false)
                }
                navBottom.navigate(R.id.fragment_search)
                true
            }

            R.id.fragment_profile -> {
                if (logged) {
                    bottom(BottomSheetBehavior.STATE_HIDDEN)
                    navController.popBackStacked(R.id.fragment_home, false)
                    navController.navigated(R.id.fragment_profile)
                }
                logged
            }

            else -> false
        }
    }

    fun initBadge() {
        viewModelScope.launch {
            _badge.emit(localRepo.countNotes())
        }
    }

    fun badge(value: Int) {
        viewModelScope.launch {
            _badge.emit(value)
        }
    }

    fun bottom(state: Int = BottomSheetBehavior.STATE_HIDDEN) {
        viewModelScope.launch {
            _bottom.emit(state)
        }
    }

    fun progress(value: Boolean) {
        viewModelScope.launch {
            _progress.emit(value)
        }
    }
}