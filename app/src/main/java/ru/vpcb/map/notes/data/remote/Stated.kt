package ru.vpcb.map.notes.data.remote

import android.content.Context

interface Stated {
    fun isNotMetered(context: Context?): Boolean

    fun isConnected(context: Context?): Boolean
    fun isConnected():Boolean
}