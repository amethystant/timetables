package com.patlejch.timetables.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.patlejch.timetables.Config
import com.patlejch.timetables.data.usecase.GetEventsFilteredUseCase
import com.patlejch.timetables.model.notification.NotificationManager
import com.patlejch.timetables.util.timeZoneBritish
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

class DailyNotificationWorker(context: Context, parameters: WorkerParameters) :
    CoroutineWorker(context, parameters),
    KoinComponent {

    companion object {
        const val NAME = "DailyNotificationWorker"
    }

    private val config: Config by inject()
    private val notificationManager: NotificationManager by inject()
    private val getEventsFilteredUseCase: GetEventsFilteredUseCase by inject()

    override suspend fun doWork(): Result {
        runCatching {
            val date = Calendar.getInstance(timeZoneBritish).run {
                if (config.notificationDayBefore)
                    add(Calendar.DAY_OF_YEAR, 1)
                time
            }

            getEventsFilteredUseCase(date).sortedBy { it.startHour }.apply {
                if (isNotEmpty()) {
                    notificationManager.displayDailyNotification(
                        first(),
                        count(),
                        config.notificationDayBefore
                    )
                }
            }
        }

        return Result.success()
    }
}
