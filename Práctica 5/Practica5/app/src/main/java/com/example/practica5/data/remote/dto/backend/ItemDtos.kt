package com.example.practica5.data.remote.dto.backend

data class ItemDto(
    val id: Int,
    val title: String,
    val description: String?,
    val category: String,
    val rating: Double,
    val updated_at: Long,
    val created_at: Long
)
