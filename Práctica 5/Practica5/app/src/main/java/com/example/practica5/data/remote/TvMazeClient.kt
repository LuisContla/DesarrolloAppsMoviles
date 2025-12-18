package com.example.practica5.data.remote

import com.example.practica5.data.remote.api.TvMazeApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TvMazeClient {
    private const val BASE_URL = "https://api.tvmaze.com/"

    val api: TvMazeApi by lazy {
        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
        val client = OkHttpClient.Builder().addInterceptor(logger).build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TvMazeApi::class.java)
    }
}
