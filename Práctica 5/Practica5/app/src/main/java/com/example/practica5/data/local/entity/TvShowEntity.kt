package com.example.practica5.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_shows")
data class TvShowEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val genresCsv: String?,
    val summary: String?,
    val imageUrl: String?,
    val cachedAt: Long
)
