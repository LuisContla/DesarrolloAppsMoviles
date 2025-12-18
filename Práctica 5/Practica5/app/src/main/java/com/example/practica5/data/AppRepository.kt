package com.example.practica5.data

import android.content.Context
import com.example.practica5.data.local.AppDb
import com.example.practica5.data.local.entity.*
import com.example.practica5.data.remote.BackendClient
import com.example.practica5.data.remote.TvMazeClient
import com.example.practica5.data.remote.dto.backend.FavoriteReq
import com.example.practica5.data.remote.dto.backend.HistoryReq
import kotlinx.coroutines.flow.Flow

class AppRepository(context: Context) {
    private val db = AppDb.get(context)
    private val dao = db.dao()
    private val session = SessionStore(context)
    private val backend = BackendClient.backendApi(context)
    private val tv = TvMazeClient.api

    fun cachedSearch(q: String): Flow<List<TvShowEntity>> = dao.cachedSearch(q)
    fun favoritesFlow(userId: Int): Flow<List<FavoriteEntity>> = dao.favorites(userId)

    suspend fun searchTvMaze(query: String): List<com.example.practica5.data.local.entity.TvShowEntity> {
        val userId = session.getUserId() ?: return emptyList()

        // Historial local
        dao.addHistory(
            com.example.practica5.data.local.entity.SearchHistoryEntity(
                userId = userId,
                query = query,
                source = "TVMAZE",
                createdAt = System.currentTimeMillis(),
                pendingSync = true
            )
        )
        // intenta subir al backend (si falla, lo sube el worker)
        runCatching { backend.addHistory(com.example.practica5.data.remote.dto.backend.HistoryReq(query, "TVMAZE")) }

        // Red TVMaze
        val resp = tv.searchShows(query)
        val mapped = resp.map { s ->
            val show = s.show
            com.example.practica5.data.local.entity.TvShowEntity(
                id = show.id,
                name = show.name,
                genresCsv = show.genres?.joinToString(","),
                summary = show.summary,
                imageUrl = show.image?.medium ?: show.image?.original,
                cachedAt = System.currentTimeMillis()
            )
        }

        // Cache
        dao.upsertShows(mapped)
        return mapped
    }


    suspend fun addFavoriteFromShow(show: TvShowEntity) {
        val userId = session.getUserId() ?: return
        dao.upsertFavorite(
            FavoriteEntity(
                userId = userId,
                source = "TVMAZE",
                itemId = show.id.toString(),
                title = show.name,
                imageUrl = show.imageUrl,
                genresCsv = show.genresCsv,
                summary = show.summary,
                createdAt = System.currentTimeMillis(),
                pendingSync = true
            )
        )
        // intenta sync inmediato (si falla, worker lo sube)
        runCatching {
            backend.addFavorite(
                FavoriteReq(
                    source = "TVMAZE",
                    itemId = show.id.toString(),
                    title = show.name,
                    imageUrl = show.imageUrl,
                    genres = show.genresCsv,
                    summary = show.summary
                )
            )
        }
    }

    suspend fun logout() = session.clear()

    suspend fun getSessionText(): String {
        val name = session.getName() ?: "-"
        val email = session.getEmail() ?: "-"
        val role = session.getRole() ?: "-"
        return "Sesión: $name ($role) - $email"
    }

    suspend fun isAdmin(): Boolean = (session.getRole() == "ADMIN")

    suspend fun adminSummary(): String {
        if (!isAdmin()) return "No eres admin."
        val users = backend.adminUsers()
        return buildString {
            append("Usuarios:\n")
            users.take(20).forEach { u ->
                append("- ${u.id}: ${u.name} (${u.role}) ${u.email}\n")
            }
        }
    }

    suspend fun recommendations(): List<TvShowEntity> {
        val userId = session.getUserId() ?: return emptyList()
        val favs = dao.pendingFavorites() // aquí reutilizo; mejor sería query favoritos normales
        // Para simpleza: usa cache de shows y devuelve los más recientes
        return dao.latestCachedShows().take(20)
    }

    // Sync pendientes (lo llama WorkManager)
    suspend fun syncPending() {
        // Historial pendiente
        val pendingH = dao.pendingHistory()
        pendingH.forEach { h ->
            runCatching {
                backend.addHistory(HistoryReq(h.query, h.source))
            }.onSuccess {
                dao.markHistorySynced(h.id)
            }
        }

        // Favoritos pendientes
        val pendingF = dao.pendingFavorites()
        pendingF.forEach { f ->
            runCatching {
                backend.addFavorite(
                    FavoriteReq(
                        source = f.source,
                        itemId = f.itemId,
                        title = f.title,
                        imageUrl = f.imageUrl,
                        genres = f.genresCsv,
                        summary = f.summary
                    )
                )
            }.onSuccess {
                dao.markFavSynced(f.id)
            }
        }
    }

    suspend fun getAdminUsers() = backend.adminUsers()

    suspend fun removeFavorite(f: com.example.practica5.data.local.entity.FavoriteEntity) {
        val userId = session.getUserId() ?: return
        dao.deleteFavorite(userId, f.source, f.itemId)

        // intenta borrar en backend (si falla, podrías manejar pending delete; por ahora simple)
        runCatching { backend.deleteFavorite(f.source, f.itemId) }
    }

    fun itemsFlow() = dao.itemsFlow()

    suspend fun refreshItems(
        q: String? = null,
        category: String? = null,
        minRating: Double? = null,
        sort: String = "updated_desc"
    ) {
        val remote = backend.getItems(
            q = q,
            category = category,
            minRating = minRating,
            sort = sort,
            limit = 50,
            offset = 0
        )
        val mapped = remote.map {
            com.example.practica5.data.local.entity.ItemEntity(
                id = it.id,
                title = it.title,
                description = it.description,
                category = it.category,
                rating = it.rating,
                updatedAt = it.updated_at,
                createdAt = it.created_at
            )
        }

        dao.replaceItems(mapped)   // <- para que SOLO se vea lo filtrado
    }

    suspend fun searchItems(query: String): List<com.example.practica5.data.local.entity.ItemEntity> {
        val userId = session.getUserId() ?: return emptyList()

        // historial local
        dao.addHistory(
            com.example.practica5.data.local.entity.SearchHistoryEntity(
                userId = userId,
                query = query,
                source = "ITEMS",
                createdAt = System.currentTimeMillis(),
                pendingSync = true
            )
        )
        runCatching { backend.addHistory(com.example.practica5.data.remote.dto.backend.HistoryReq(query, "ITEMS")) }

        // backend filtrado
        val remote = backend.getItems(q = query, sort = "updated_desc", limit = 50, offset = 0)

        // map a Entity (para cache offline si lo quieres)
        val mapped = remote.map {
            com.example.practica5.data.local.entity.ItemEntity(
                id = it.id,
                title = it.title,
                description = it.description,
                category = it.category,
                rating = it.rating,
                updatedAt = it.updated_at,
                createdAt = it.created_at
            )
        }

        // Guarda para offline (no borres tabla aquí, solo upsert)
        dao.upsertItems(mapped)

        return mapped
    }

    suspend fun addFavoriteFromItem(item: com.example.practica5.data.local.entity.ItemEntity) {
        val userId = session.getUserId() ?: return

        val fav = com.example.practica5.data.local.entity.FavoriteEntity(
            id = 0,
            userId = userId,
            source = "ITEMS",
            itemId = item.id.toString(),
            title = item.title,
            imageUrl = null,
            genresCsv = "Cat: ${item.category} | Rating: ${item.rating}",
            summary = item.description,
            createdAt = System.currentTimeMillis(),
            pendingSync = true
        )

        dao.upsertFavorite(fav)

        runCatching {
            backend.addFavorite(
                com.example.practica5.data.remote.dto.backend.FavoriteReq(
                    source = "ITEMS",
                    itemId = item.id.toString(),
                    title = item.title,
                    imageUrl = null,
                    genres = fav.genresCsv,
                    summary = item.description
                )
            )
        }
    }

}
