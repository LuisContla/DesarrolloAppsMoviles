package com.example.practica5.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorites",
    indices = [Index(value=["userId","source","itemId"], unique = true)]
)
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Int,
    val source: String, // "TVMAZE"
    val itemId: String, // show id
    val title: String,
    val imageUrl: String?,
    val genresCsv: String?,
    val summary: String?,
    val createdAt: Long,
    val pendingSync: Boolean
)
