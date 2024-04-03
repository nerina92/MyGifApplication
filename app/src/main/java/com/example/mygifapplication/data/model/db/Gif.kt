package com.example.mygifapplication.data.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "gif")
data class Gif(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val title: String,
    val url: String,
    val altText: String,
    val importDate: String,
    val dateTrending: String
)

