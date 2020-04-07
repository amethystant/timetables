package com.patlejch.timetables.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.patlejch.timetables.data.usecase.SyncUseCase
import com.patlejch.timetables.model.notification.NotificationManager
import org.koin.core.KoinComponent
import org.koin.core.inject

class SyncWorker(context: Context, parameters: WorkerParameters) :
    CoroutineWorker(context, parameters),
    KoinComponent {

    companion object {
        const val NAME = "SyncWorker"
        const val NAME_ONE_TIME = "SyncWorker_onetime"
    }

    private val notificationManager: NotificationManager by inject()
    private val syncUseCase: SyncUseCase by inject()

    override suspend fun doWork(): Result {
        runCatching { notificationManager.scheduleDailyNotifications() }
        runCatching { syncUseCase() }
        return Result.success()
    }
}
