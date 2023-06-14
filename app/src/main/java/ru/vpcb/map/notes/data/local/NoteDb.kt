package ru.vpcb.map.notes.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.hilt.android.EntryPointAccessors
import ru.vpcb.map.notes.R
import ru.vpcb.map.notes.model.converter.Converters
import ru.vpcb.map.notes.model.Note
import ru.vpcb.map.notes.model.User
import timber.log.Timber

const val DATABASE_NOTE = "database_notes"

@Database(entities = [Note::class, User::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class NoteDb : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun userDao(): UserDao

    class Callback(private val context: Context) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Timber.d("Room: init ${context.getString(R.string.app_name)}")
        }

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            Timber.d("Room: open")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: NoteDb? = null

        private fun pointAdapter(context: Context) = EntryPointAccessors
            .fromApplication(context, Converters.ConvertersEntryPoint::class.java)
            .providePointAdapter()
        fun instance(context: Context): NoteDb =
            INSTANCE ?: synchronized(this) {

                INSTANCE ?: Room.databaseBuilder(context, NoteDb::class.java, DATABASE_NOTE)
                    .addCallback(Callback(context))
                    .addTypeConverter(Converters(pointAdapter(context)))
                    .fallbackToDestructiveMigration()
                    .build().also {
                        INSTANCE = it
                    }
            }
    }

}