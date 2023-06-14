package ru.vpcb.map.notes.model.converter

import android.graphics.Point
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson

@JsonClass(generateAdapter = true)
data class PointJson(
    val x:Int,
    val y:Int
)

class PointJsonAdapter {
    @ToJson
    fun toJson(value: Point) = PointJson(value.x, value.y)
    @FromJson
    fun fromJson(value: PointJson) = Point(value.x, value.y)
}
