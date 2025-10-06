package com.example.gameverse.data.models

import androidx.annotation.DrawableRes

data class Genre(
    val id: String,
    val name: String,
    @DrawableRes val bgRes: Int   // <- el que ya usabas (bg_rpg, bg_arcade, etc.)
) {
    // Alias para el adapter/neón:
    @get:DrawableRes
    val artRes: Int get() = bgRes
}
