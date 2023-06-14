package ru.vpcb.map.notes.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.vpcb.map.notes.model.Note
import ru.vpcb.map.notes.model.TABLE_NOTES

@Dao
interface NoteDao {

    @Query("SELECT * FROM $TABLE_NOTES")
    fun notes(): Flow<List<Note>>

    @Query("SELECT * FROM $TABLE_NOTES")
    suspend fun query(): List<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<Note>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(item: Note)

    @Delete
    suspend fun delete(item: Note)

    @Query("DELETE FROM $TABLE_NOTES")
    suspend fun clear()

    @Query("SELECT COUNT(*) FROM $TABLE_NOTES")
    suspend fun count(): Int

    @Transaction
    suspend fun refresh(items: List<Note>) {
        clear()
        insert(items)
    }

}