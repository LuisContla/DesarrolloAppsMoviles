package com.example.practica5.data.remote.api

import com.example.practica5.data.remote.dto.tvmaze.TvMazeSearchItem
import com.example.practica5.data.remote.dto.tvmaze.TvShowDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvMazeApi {
    @GET("search/shows")
    suspend fun searchShows(@Query("q") q: String): List<TvMazeSearchItem>

    @GET("shows/{id}")
    suspend fun getShow(@Path("id") id: Int): TvShowDto
}
