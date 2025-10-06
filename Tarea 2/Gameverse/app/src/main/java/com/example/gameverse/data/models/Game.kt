package com.example.gameverse.data.models

data class Game(
    val id: String,
    val franchiseId: String,
    val title: String,
    val coverRes: Int,    // portada
    val backdropRes: Int, // (ya no es mapa; puedes dejarlo de fondo si quieres)

    // 🔽 Campos nuevos (opcionales)
    val year: Int? = null,
    val developers: List<String> = emptyList(),
    val publisher: String? = null,
    val platforms: List<String> = emptyList(),
    val description: String? = null,
    val facts: List<String> = emptyList()
)
