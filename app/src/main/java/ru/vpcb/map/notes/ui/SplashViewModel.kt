package ru.vpcb.map.notes.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.vpcb.map.notes.data.local.LocalRepo
import ru.vpcb.map.notes.data.remote.FirebaseRepo
import ru.vpcb.map.notes.data.remote.Stated
import ru.vpcb.map.notes.model.MResult
import ru.vpcb.map.notes.ui.auth.Signed
import ru.vpcb.map.notes.utils.dispatchCallback
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val localRepo: LocalRepo,
    private val firebaseRepo: FirebaseRepo
) : ViewModel() {

    private val _state = MutableSharedFlow<Boolean>()
    val state = _state.asSharedFlow()

    fun init(context: Context, stated: Stated, signed: Signed) {
        viewModelScope.launch(dispatchCallback()) {
            try {
                val empty = localRepo.countNotes() == 0
                signed.userExpired()
                val logged = signed.logged()
                if (empty && stated.isConnected(context) && logged) {
                    val result = firebaseRepo.listNotes()
                    if (result is MResult.Success) {
                        localRepo.refreshNotes(result.data)
                    }
                }
            } catch (e: Exception) {
                Timber.d(e)
            }
            _state.emit(true)
        }
    }
}