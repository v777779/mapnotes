package ru.vpcb.map.notes.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Location(
    val lat: Double? = null,
    val lon: Double? = null,
    val timestamp:Long = System.currentTimeMillis()
)