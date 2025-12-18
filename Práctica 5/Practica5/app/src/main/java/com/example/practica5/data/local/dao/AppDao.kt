package com.example.practica5.data.local.dao

import androidx.room.*
import com.example.practica5.data.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    // TV cache
    @Query("SELECT * FROM tv_shows WHERE name LIKE '%' || :q || '%' ORDER BY cachedAt DESC")
    fun cachedSearch(q: String): Flow<List<TvShowEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertShows(items: List<TvShowEntity>)

    // Favorites
    @Query("SELECT * FROM favorites WHERE userId=:userId ORDER BY createdAt DESC")
    fun favorites(userId: Int): Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertFavorite(f: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE userId=:userId AND source=:source AND itemId=:itemId")
    suspend fun deleteFavorite(userId: Int, source: String, itemId: String)

    @Query("SELECT * FROM favorites WHERE pendingSync=1 LIMIT 50")
    suspend fun pendingFavorites(): List<FavoriteEntity>

    @Query("UPDATE favorites SET pendingSync=0 WHERE id=:id")
    suspend fun markFavSynced(id: Long)

    // History
    @Query("SELECT * FROM search_history WHERE userId=:userId ORDER BY createdAt DESC LIMIT 50")
    fun history(userId: Int): Flow<List<SearchHistoryEntity>>

    @Insert
    suspend fun addHistory(h: SearchHistoryEntity)

    @Query("SELECT * FROM search_history WHERE pendingSync=1 LIMIT 50")
    suspend fun pendingHistory(): List<SearchHistoryEntity>

    @Query("UPDATE search_history SET pendingSync=0 WHERE id=:id")
    suspend fun markHistorySynced(id: Long)

    // Items
    @androidx.room.Query("SELECT * FROM items ORDER BY updatedAt DESC")
    fun itemsFlow(): kotlinx.coroutines.flow.Flow<List<com.example.practica5.data.local.entity.ItemEntity>>

    @androidx.room.Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun upsertItems(items: List<com.example.practica5.data.local.entity.ItemEntity>)

    @Query("DELETE FROM items")
    suspend fun clearItems()

    @Transaction
    suspend fun replaceItems(items: List<com.example.practica5.data.local.entity.ItemEntity>) {
        clearItems()
        upsertItems(items)
    }

    // Reco helper
    @Query("SELECT * FROM tv_shows ORDER BY cachedAt DESC LIMIT 200")
    suspend fun latestCachedShows(): List<TvShowEntity>
}
