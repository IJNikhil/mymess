package com.example.mymess.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            // Simulate heavy background sync
            delay(2000)
            // Log sync success (in real app, fetch from network and update DB)
            // For now, we just pretend
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
