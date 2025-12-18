package com.example.practica5.data.remote.dto.tvmaze

data class TvMazeSearchItem(
    val score: Double,
    val show: TvShowDto
)

data class TvShowDto(
    val id: Int,
    val name: String,
    val genres: List<String>?,
    val summary: String?,
    val image: TvImageDto?
)

data class TvImageDto(
    val medium: String?,
    val original: String?
)
