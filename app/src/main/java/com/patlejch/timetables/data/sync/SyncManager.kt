package com.patlejch.timetables.data.sync

import android.content.Context
import androidx.work.*
import com.patlejch.timetables.work.SyncWorker
import java.util.concurrent.TimeUnit

class SyncManager(private val context: Context) {

    fun syncNow() {
        val workRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            SyncWorker.NAME_ONE_TIME,
            ExistingWorkPolicy.APPEND,
            workRequest
        )
    }

    fun schedulePeriodicSync() {
        val workRequest = PeriodicWorkRequestBuilder<SyncWorker>(1, TimeUnit.HOURS)
            .setConstraints(
                Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            ).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            SyncWorker.NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}
