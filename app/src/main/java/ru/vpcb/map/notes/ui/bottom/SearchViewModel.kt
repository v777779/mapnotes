package ru.vpcb.map.notes.ui.bottom

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.JsonAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.vpcb.map.notes.data.local.LocalRepo
import ru.vpcb.map.notes.data.remote.FirebaseRepo
import ru.vpcb.map.notes.data.remote.Stated
import ru.vpcb.map.notes.databinding.FragmentAddBinding
import ru.vpcb.map.notes.di.QNoteAdapter
import ru.vpcb.map.notes.location.Located
import ru.vpcb.map.notes.model.MResult
import ru.vpcb.map.notes.model.Note
import ru.vpcb.map.notes.ui.Noted
import ru.vpcb.map.notes.ui.auth.Signed
import ru.vpcb.map.notes.utils.EMPTY_STRING
import ru.vpcb.map.notes.utils.ERROR_AUTH
import ru.vpcb.map.notes.utils.ERROR_CONNECT
import ru.vpcb.map.notes.utils.ERROR_FB_NOTES
import ru.vpcb.map.notes.utils.dispatchCallback
import ru.vpcb.map.notes.utils.toDoubleOrNull
import ru.vpcb.map.notes.utils.toMResult
import ru.vpcb.map.notes.utils.toNotNullOrEmpty
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class SearchViewModel @Inject constructor(
    @QNoteAdapter private val noteAdapter: JsonAdapter<Note>,
    private val localRepo: LocalRepo,
    private val firebaseRepo: FirebaseRepo
) : ViewModel() {

    private val _register = MutableSharedFlow<MResult<List<Note>>>()
    val register = _register.asSharedFlow()

    private val _notes = MutableSharedFlow<List<Note>>()
    val notes = _notes.asSharedFlow()
//    val notes = localRepo.notes()

    private val _note = MutableSharedFlow<Note>()
    val note = _note.asSharedFlow()


    private val dispatchCallback = dispatchCallback {
        viewModelScope.launch {
            _register.emit(MResult.Error(ERROR_FB_NOTES, it.message ?: EMPTY_STRING))
        }
    }

    /**
     *  delete > firebaseRepo > valueCallback > updateLocal > emit(notes> adapter submit
     */
    fun updateNotes(notes: List<Note>) {
        viewModelScope.launch {
            localRepo.refreshNotes(notes)
            _notes.emit(notes)
        }
    }

    private fun updateNote(binding: FragmentAddBinding, noted: Noted) {
        val note = noted.currentNote ?: Note()
        note.apply {
            this.title = binding.editTitle.toNotNullOrEmpty()
            this.body = binding.editBody.toNotNullOrEmpty()
            this.lat = binding.editLat.toDoubleOrNull()
            this.lon = binding.editLon.toDoubleOrNull()
        }
        noted.currentNote = note
    }

    fun updateNote(binding: FragmentAddBinding, noted: Noted, located: Located?) {
        val note = noted.currentNote ?: Note()
        note.apply {
            this.title = binding.editTitle.toNotNullOrEmpty()
            this.body = binding.editBody.toNotNullOrEmpty()
            this.lat = located?.currentLocation?.lat
            this.lon = located?.currentLocation?.lon
        }
        noted.currentNote = note
    }

    fun createNote(binding: FragmentAddBinding, noted: Noted, stated: Stated, signed: Signed) {
        updateNote(binding, noted)
        note(stated, signed, noted.currentNote, FirebaseRepo.Mode.ADD)
    }

    @SuppressLint("NewApi")
    private suspend fun addressCoroutine(geocoder: Geocoder?, lat: Double?, lon: Double?): Address? {
        if (geocoder == null || lat == null || lon == null) return null
        return suspendCoroutine { cont ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocation(lat, lon, 1) {
                    cont.resume(if (it.isEmpty()) null else it[0])
                }
            } else {
                geocoder.getFromLocation(lat, lon, 1) {
                    cont.resume(if (it.isEmpty()) null else it[0])
                }
            }
        }
    }

    fun initNote(geocoder: Geocoder?, noted: Noted, located: Located) {
        viewModelScope.launch {
            val address = addressCoroutine(
                geocoder, located.currentLocation?.lat, located.currentLocation?.lon
            )?.getAddressLine(0) ?: EMPTY_STRING
            val note = Note(
                body = address,
                lat = located.currentLocation?.lat,
                lon = located.currentLocation?.lon,
            )
            noted.currentNote = note
            _note.emit(note)
        }

    }

    private suspend fun protected(stated: Stated, signed: Signed, action: suspend () -> Unit) {
        try {
            if (!stated.isConnected()) _register.emit(MResult.Error(ERROR_CONNECT))
            else if (!signed.logged()) _register.emit(MResult.Error(ERROR_AUTH))
            else {
                _register.emit(MResult.Loading)
                action()
            }
        } catch (e: Exception) {
            Timber.d(e)
            _register.emit(e.toMResult(ERROR_FB_NOTES))
        }
    }

    private suspend fun updateList() {
        val result = firebaseRepo.listNotes()
        if (result is MResult.Success) localRepo.refreshNotes(result.data)
        _register.emit(result)
    }

    fun note(stated: Stated, signed: Signed, note: Note?, mode: FirebaseRepo.Mode) {
        viewModelScope.launch(dispatchCallback) {
            protected(stated, signed) {
                when (val result = firebaseRepo.setNote(note, mode)) {
                    is MResult.Success -> _register.emit(MResult.Success(emptyList()))
                    is MResult.Error -> _register.emit(MResult.Error(result.code, result.message))
                    else -> _register.emit(MResult.None)
                }
            }
        }
    }

    fun note(stated: Stated, signed: Signed, noted: Noted, mode: FirebaseRepo.Mode) {
        note(stated, signed, noted.currentNote, mode)
    }

    fun notes(stated: Stated, signed: Signed, force: Boolean = false) {
        viewModelScope.launch(dispatchCallback) {
            try {
                if (!force) {
                    val notes = localRepo.queryNotes()
                    _notes.emit(notes)
                } else {
                    protected(stated, signed) {
                        val result = firebaseRepo.listNotes()
                        _register.emit(result)
                    }
                }
            } catch (e: Exception) {
                Timber.d(e)
                _register.emit(e.toMResult(ERROR_FB_NOTES))
            }
        }
    }

}