package com.example.practica5.data.remote.api

import com.example.practica5.data.remote.dto.backend.*
import retrofit2.http.*

interface BackendApi {

    // AUTH
    @POST("auth/login")
    suspend fun login(@Body req: LoginReq): AuthResp

    @POST("auth/register")
    suspend fun register(@Body req: RegisterReq): AuthResp

    @GET("me")
    suspend fun me(): MeResp


    // HISTORY
    @GET("history")
    suspend fun myHistory(): List<SearchHistoryDto>

    @POST("history")
    suspend fun addHistory(@Body req: HistoryReq): IdResp


    // FAVORITES
    @GET("favorites")
    suspend fun myFavorites(): List<FavoriteDto>

    @POST("favorites")
    suspend fun addFavorite(@Body req: FavoriteReq): OkResp

    @DELETE("favorites/{source}/{itemId}")
    suspend fun deleteFavorite(
        @Path("source") source: String,
        @Path("itemId") itemId: String
    ): OkResp

    // ITEMS
    @GET("items")
    suspend fun getItems(
        @Query("q") q: String? = null,
        @Query("category") category: String? = null,
        @Query("minRating") minRating: Double? = null,
        @Query("sort") sort: String? = null,
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null
    ): List<ItemDto>

    // ADMIN
    @GET("admin/users")
    suspend fun adminUsers(): List<UserDto>

    @GET("admin/history/{userId}")
    suspend fun adminHistory(@Path("userId") userId: Int): List<SearchHistoryDto>

    @GET("admin/favorites/{userId}")
    suspend fun adminFavorites(@Path("userId") userId: Int): List<FavoriteDto>
}
