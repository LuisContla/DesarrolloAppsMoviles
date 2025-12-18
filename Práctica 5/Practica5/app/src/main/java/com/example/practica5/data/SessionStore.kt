package com.example.practica5.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.practica5.data.remote.dto.backend.UserDto
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore("session")

class SessionStore(private val context: Context) {
    private val KEY_TOKEN = stringPreferencesKey("token")
    private val KEY_ID = intPreferencesKey("user_id")
    private val KEY_NAME = stringPreferencesKey("name")
    private val KEY_EMAIL = stringPreferencesKey("email")
    private val KEY_ROLE = stringPreferencesKey("role")

    suspend fun save(token: String, user: UserDto) {
        context.dataStore.edit {
            it[KEY_TOKEN] = token
            it[KEY_ID] = user.id
            it[KEY_NAME] = user.name
            it[KEY_EMAIL] = user.email
            it[KEY_ROLE] = user.role
        }
    }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }

    suspend fun getToken(): String? = context.dataStore.data.first()[KEY_TOKEN]
    suspend fun getUserId(): Int? = context.dataStore.data.first()[KEY_ID]
    suspend fun getName(): String? = context.dataStore.data.first()[KEY_NAME]
    suspend fun getEmail(): String? = context.dataStore.data.first()[KEY_EMAIL]
    suspend fun getRole(): String? = context.dataStore.data.first()[KEY_ROLE]
}
