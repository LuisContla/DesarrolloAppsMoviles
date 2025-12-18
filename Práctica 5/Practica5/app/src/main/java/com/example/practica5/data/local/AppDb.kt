package com.example.practica5.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.practica5.data.local.dao.AppDao
import com.example.practica5.data.local.entity.*

@Database(
    entities = [
        TvShowEntity::class,
        FavoriteEntity::class,
        SearchHistoryEntity::class,
        ItemEntity::class
    ],
    version = 2, // <- SUBE LA VERSION (antes 1)
    exportSchema = false
)
abstract class AppDb : RoomDatabase() {

    abstract fun dao(): AppDao

    companion object {
        @Volatile private var INSTANCE: AppDb? = null

        fun get(context: Context): AppDb =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDb::class.java,
                    "app.db"
                )
                    // En desarrollo: si cambia el schema, borra y recrea la BD
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
    }
}
