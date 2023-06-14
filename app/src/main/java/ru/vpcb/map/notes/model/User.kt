package ru.vpcb.map.notes.model

import androidx.room.Entity
import androidx.room.PrimaryKey

const val TABLE_USERS  = "users"

@Entity(tableName = TABLE_USERS)
data class User(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val key:String? = null,
    val name:String? = null,
)
