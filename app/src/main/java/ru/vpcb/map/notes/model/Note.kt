package ru.vpcb.map.notes.model

import android.graphics.Point
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.DataSnapshot
import com.squareup.moshi.JsonClass
import ru.vpcb.map.notes.utils.EMPTY_STRING

const val TABLE_NOTES = "notes"

@JsonClass(generateAdapter = true)
@Entity(tableName = TABLE_NOTES)
data class Note(
    @PrimaryKey(autoGenerate = false)
    var key: String = EMPTY_STRING,
    var title: String? = null,
    var body: String? = null,
    var user: String? = null,
    var image: String? = null,
    var video: String? = null,
    var audio: String? = null,
    var lat: Double? = null,
    var lon: Double? = null,
    var screen: Point? = null,
    var show: Boolean? = null,
)

fun DataSnapshot.toNote(): Note? =
    try {
        getValue(Note::class.java)?.also { it.key = key!! }
    } catch (e: Exception) {
        null
    }
