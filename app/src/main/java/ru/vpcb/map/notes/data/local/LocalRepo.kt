package ru.vpcb.map.notes.data.local

import ru.vpcb.map.notes.model.Note
import ru.vpcb.map.notes.model.User
import ru.vpcb.map.notes.utils.CryptoPrefs
import javax.inject.Inject

class LocalRepo @Inject constructor( noteDb: NoteDb) {

    private val noteDao = noteDb.noteDao()
    private val userDao = noteDb.userDao()

    suspend fun refreshUsers(items:List<User>) = userDao.refresh(items)

    suspend fun clearUsers() = userDao.clear()
    suspend fun deleteUser(item:User) = userDao.delete(item)

    suspend fun updateUser(item:User) = userDao.update(item)

    suspend fun insertUser(item: User) = userDao.insert(item)

    suspend fun queryUsers() = userDao.query()

    fun users() = userDao.users()


    suspend fun refreshNotes(items:List<Note>) {
        noteDao.refresh(items)
        CryptoPrefs.setNotes()
    }
    suspend fun countNotes() = noteDao.count()
    suspend fun clearNotes() = noteDao.clear()
    suspend fun deleteNote(item:Note) = noteDao.delete(item)

    suspend fun updateNote(item:Note) = noteDao.update(item)

    suspend fun insertNote(item:Note) = noteDao.insert(item)

    suspend fun queryNotes() = noteDao.query()

    fun notes() = noteDao.notes()

}