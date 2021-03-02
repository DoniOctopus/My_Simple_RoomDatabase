package com.enigmacamp.mybasicroomdatabase.room

import androidx.room.Entity
import androidx.room.PrimaryKey

//Model/Entity untuk catatan model kita
@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val title: String,
    val note: String
)