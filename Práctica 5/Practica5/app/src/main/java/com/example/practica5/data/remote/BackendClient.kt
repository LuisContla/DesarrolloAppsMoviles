package com.example.practica5.data.remote

import android.content.Context
import com.example.practica5.data.SessionStore
import com.example.practica5.data.remote.api.BackendApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BackendClient {

    // EJEMPLO: "http://192.168.1.50:3000/"
    const val BASE_URL = "http://10.51.5.37:3000/"

    fun backendApi(context: Context): BackendApi {
        val session = SessionStore(context)

        val authInterceptor = Interceptor { chain ->
            val req = chain.request()
            val token = runCatching { kotlinx.coroutines.runBlocking { session.getToken() } }.getOrNull()
            val newReq = if (!token.isNullOrBlank()) {
                req.newBuilder().addHeader("Authorization", "Bearer $token").build()
            } else req
            chain.proceed(newReq)
        }

        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(logger)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BackendApi::class.java)
    }
}
