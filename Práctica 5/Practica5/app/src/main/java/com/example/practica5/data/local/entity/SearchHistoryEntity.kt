package com.example.practica5.data.local.entity
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_history")
data class SearchHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Int,
    val query: String,
    val source: String, // "TVMAZE"
    val createdAt: Long,
    val pendingSync: Boolean
)
