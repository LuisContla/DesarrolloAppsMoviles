package com.example.practica5.data.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.practica5.data.AppRepository

class SyncWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        return try {
            AppRepository(applicationContext).syncPending()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
