package com.example.gameverse.data.models

data class Poi(
    val id: String,
    val gameId: String,
    val title: String,
    val description: String,
    val thumbRes: Int,    // miniatura del POI
    val x: Float,         // 0..1 posición relativa en X
    val y: Float          // 0..1 posición relativa en Y
)
