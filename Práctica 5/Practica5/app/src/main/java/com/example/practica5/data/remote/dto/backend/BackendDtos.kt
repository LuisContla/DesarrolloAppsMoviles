package com.example.practica5.data.remote.dto.backend

// ---------- REQUESTS ----------
data class LoginReq(
    val email: String,
    val password: String
)

data class RegisterReq(
    val name: String,
    val email: String,
    val password: String,
    val role: String? = null // "USER" o "ADMIN"
)

data class HistoryReq(
    val query: String,
    val source: String // "TVMAZE"
)

data class FavoriteReq(
    val source: String,  // "TVMAZE"
    val itemId: String,  // show id
    val title: String,
    val imageUrl: String? = null,
    val genres: String? = null,
    val summary: String? = null
)

// ---------- RESPONSES ----------
data class AuthResp(
    val token: String,
    val user: UserDto
)

data class UserDto(
    val id: Int,
    val name: String,
    val email: String,
    val role: String
)

data class MeResp(
    val user: MeUserDto
)

data class MeUserDto(
    val sub: Int,          // viene del JWT: userId
    val role: String,
    val name: String,
    val email: String
)

data class SearchHistoryDto(
    val id: Long,
    val user_id: Int,
    val query: String,
    val source: String,
    val created_at: Long
)

data class FavoriteDto(
    val id: Long,
    val user_id: Int,
    val source: String,
    val item_id: String,
    val title: String,
    val image_url: String?,
    val genres: String?,
    val summary: String?,
    val created_at: Long
)

data class IdResp(val id: Long)
data class OkResp(val ok: Boolean)
