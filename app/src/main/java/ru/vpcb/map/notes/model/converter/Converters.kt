package ru.vpcb.map.notes.model.converter

import android.graphics.Point
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.vpcb.map.notes.di.QPointAdapter

@ProvidedTypeConverter
class Converters(private val pointAdapter: JsonAdapter<Point>) {

    @TypeConverter
    fun fromPointToString(value: Point?): String? {
        return value?.let {
            pointAdapter.toJson(it)
        }
    }

    @TypeConverter
    fun fromStringToPoint(value: String?): Point? {
        return value?.let {
            pointAdapter.fromJson(it)
        }
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface ConvertersEntryPoint {

        @QPointAdapter
        fun providePointAdapter(): JsonAdapter<Point>
    }
}