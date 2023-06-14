package ru.vpcb.map.notes.data.remote

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import ru.vpcb.map.notes.model.MResult
import ru.vpcb.map.notes.model.Note
import ru.vpcb.map.notes.model.toNote
import ru.vpcb.map.notes.utils.CONNECT_TIMEOUT
import ru.vpcb.map.notes.utils.EMPTY_STRING
import ru.vpcb.map.notes.utils.ERROR_FB_NOTES
import ru.vpcb.map.notes.utils.withTimeouts
import timber.log.Timber
import kotlin.coroutines.resume

const val PATH_USER = "users"
const val PATH_NOTE = "notes"

class FirebaseRepo {

    private val database = FirebaseDatabase.getInstance()
    private val storage = FirebaseStorage.getInstance()

    private var listener: ValueEventListener? = null
    fun notes() = callbackFlow {
        listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.children.mapNotNull {
                    it.toNote()
                }
                trySendBlocking(MResult.Success(items))
                    .onFailure {
                        Timber.d("Error: realtime database ${it?.message ?: EMPTY_STRING}")
                    }
            }

            override fun onCancelled(error: DatabaseError) {
                trySendBlocking(MResult.Error(code = ERROR_FB_NOTES, message = error.message))
                    .onFailure {
                        Timber.d("Error: realtime database ${it?.message ?: EMPTY_STRING}")
                    }
            }
        }

        database.getReference(PATH_NOTE).addValueEventListener(listener!!)
        awaitClose {
            database.getReference(PATH_NOTE).removeEventListener(listener!!)
        }
    }


    /**
     *  add     setValue(note)
     *  update  setValue(note)
     *  delete  setValue(null)
     *
     */
    private suspend fun setNoteCoroutine(note: Note?, mode: Mode): MResult<Boolean> {

        val key = (if (mode == Mode.ADD) database.getReference(PATH_NOTE).push().key?.also { note?.key = it }
        else note?.key)
        key ?: return MResult.Error(ERROR_FB_NOTES, EMPTY_STRING)

        return withTimeouts(CONNECT_TIMEOUT) { cont ->
            database.getReference(PATH_NOTE).child(key).setValue(if (mode == Mode.DELETE) null else note)
                .addOnSuccessListener {
                    cont.resume(MResult.Success(true))
                }
                .addOnFailureListener {
                    cont.resume(MResult.Error(ERROR_FB_NOTES, it.message ?: EMPTY_STRING))
                }

        }
    }

    suspend fun setNote(note: Note?, mode: Mode): MResult<Boolean> {
        return setNoteCoroutine(note, mode)
    }

    private suspend fun listNoteCoroutine(): MResult<List<Note>> {

        return withTimeouts(CONNECT_TIMEOUT) { cont ->
            database.getReference(PATH_NOTE).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val notes = snapshot.children.mapNotNull {
                        it.toNote()
                    }
                    cont.resume(MResult.Success(notes))
                }

                override fun onCancelled(error: DatabaseError) {
                    cont.resume(MResult.Error(ERROR_FB_NOTES, error.message))
                }
            })
        }
    }

    suspend fun listNotes(): MResult<List<Note>> {
        return listNoteCoroutine()
    }

    enum class Mode {
        ADD, UPDATE, DELETE
    }
}