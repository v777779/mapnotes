package ru.vpcb.map.notes.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.vpcb.map.notes.data.local.LocalRepo
import ru.vpcb.map.notes.data.remote.FirebaseRepo
import ru.vpcb.map.notes.databinding.LayoutCardMapBinding
import ru.vpcb.map.notes.model.MResult
import ru.vpcb.map.notes.model.Note
import ru.vpcb.map.notes.ui.auth.Signed
import ru.vpcb.map.notes.utils.EMPTY_STRING
import ru.vpcb.map.notes.utils.ERROR_FB_NOTES
import ru.vpcb.map.notes.utils.dispatchCallback
import ru.vpcb.map.notes.utils.formatCoordinates
import ru.vpcb.map.notes.utils.loadImage
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val localRepo: LocalRepo,
    private val firebaseRepo: FirebaseRepo
) : ViewModel() {

    private val _register = MutableSharedFlow<MResult<List<Note>>>()
    val register = _register.asSharedFlow()

    private val dispatchCallback = dispatchCallback {
        Timber.d(it)
    }

    fun readNotes(signed: Signed) {
        signed.userExpired()
        if (!signed.logged()) return
        viewModelScope.launch(dispatchCallback) {
            try {
                val notes = localRepo.queryNotes()
                if (notes.isNotEmpty()) {
                    _register.emit(MResult.Success(notes))
                } else {
                    _register.emit(MResult.Loading)
                    val result = firebaseRepo.listNotes()
                    if (result is MResult.Success) {
                        val items = result.data
                        localRepo.refreshNotes(items)
                        _register.emit(MResult.Success(items))
                    } else {
                        _register.emit(result)
                    }
                }
            } catch (e: Exception) {
                Timber.d(e)
                _register.emit(MResult.Error(ERROR_FB_NOTES, e.message ?: EMPTY_STRING))
            }
        }
    }

    fun hideCard(binding: LayoutCardMapBinding) = CardAnimator.hideCard(binding.root)
    fun showCard(binding: LayoutCardMapBinding, note:Note) = CardAnimator.showCard(binding.root, note).also {

        binding.textTitle.text = note.title
        binding.textBody.text = note.body
        binding.textCoord.text =  note.formatCoordinates(binding.root)
        binding.image.loadImage(note.image)

    }


}