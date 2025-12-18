package com.example.practica5.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class ItemEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String?,
    val category: String,
    val rating: Double,
    val updatedAt: Long,
    val createdAt: Long
)
