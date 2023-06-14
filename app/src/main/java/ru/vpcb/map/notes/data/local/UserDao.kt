package ru.vpcb.map.notes.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.vpcb.map.notes.model.TABLE_USERS
import ru.vpcb.map.notes.model.User


@Dao
interface UserDao {

    @Query("SELECT * FROM $TABLE_USERS")
    fun users():Flow<User>

    @Query("SELECT * FROM $TABLE_USERS")
    suspend fun query():List<User>

    @Query("SELECT * FROM $TABLE_USERS WHERE `key`=:key")
    suspend fun query(key:String):List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: User)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(item: User)

    @Delete
    suspend fun delete(item:User)

    @Query("DELETE FROM $TABLE_USERS")
    suspend fun clear()

    @Transaction
    suspend fun refresh(items:List<User>){
        clear()
        insert(items)
    }
}