package com.patlejch.timetables.model.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.patlejch.timetables.Config
import com.patlejch.timetables.R
import com.patlejch.timetables.model.entity.inbound.Event
import com.patlejch.timetables.ui.MainActivity
import com.patlejch.timetables.util.formatTimeOnly
import com.patlejch.timetables.util.notificationFormat
import com.patlejch.timetables.util.timeZoneBritish
import com.patlejch.timetables.work.DailyNotificationWorker
import java.util.*
import java.util.concurrent.TimeUnit

class NotificationManager(
    private val context: Context,
    private val config: Config
) {

    companion object {
        private const val CHANNEL_ID_TIMETABLE_CHANGES = "CHANNEL_ID_TIMETABLE_CHANGES"
        private const val CHANNEL_ID_DAILY_NOTIFICATIONS = "CHANNEL_ID_DAILY_NOTIFICATIONS"

        private const val NOTIFICATION_ID_DAILY = 1
        private const val NOTIFICATION_ID_MAX_RESERVED = 1
    }

    fun scheduleDailyNotifications() {
        val timeNow = Date()
        val notificationTime = config.notificationTime.let {
            Calendar.getInstance(timeZoneBritish).run {
                set(Calendar.HOUR_OF_DAY, it.hour)
                set(Calendar.MINUTE, it.minute)
                if (time < timeNow) {
                    add(Calendar.DAY_OF_YEAR, 1)
                }
                time
            }
        }

        val delay = notificationTime.time - timeNow.time

        val workRequest = PeriodicWorkRequestBuilder<DailyNotificationWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            DailyNotificationWorker.NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }

    fun displayTimetableChangeNotification(event: Event) {
        context.apply {
            val title = getString(
                if (event.deleted)
                    R.string.notification_event_added_title
                else
                    R.string.notification_event_removed_title
            )
            val text = getString(
                if (event.deleted)
                    R.string.notification_event_removed_text
                else
                    R.string.notification_event_added_text,
                event.summary,
                event.startDateBritish.notificationFormat(resources)
            )
            displayMainActivityNotification(
                title,
                text,
                createTimetableChangesChannel(),
                event.id.toInt()
            )
        }
    }

    fun displayTimetableChangesNotification(addedCount: Int, removedCount: Int) {
        context.apply {
            val title = getString(R.string.notification_timetable_changes_title)
            val text = getString(
                R.string.notification_timetable_changes_text,
                addedCount,
                removedCount
            )
            displayMainActivityNotification(
                title,
                text,
                createTimetableChangesChannel(),
                uniqueNotificationId()
            )
        }
    }

    fun displayDailyNotification(earliestEvent: Event, eventsCount: Int, isDayBefore: Boolean) {
        context.apply {
            val title = getString(R.string.notification_daily_title)
            val text = getString(
                if (isDayBefore)
                    R.string.notification_daily_day_before_text
                else
                    R.string.notification_daily_text,
                earliestEvent.location,
                earliestEvent.startDateBritish.formatTimeOnly(),
                earliestEvent.summary,
                eventsCount
            )
            displayMainActivityNotification(
                title,
                text,
                createDailyNotificationsChannel(),
                NOTIFICATION_ID_DAILY
            )
        }
    }

    private fun uniqueNotificationId() =
        (Date().time % Int.MAX_VALUE).toInt() + NOTIFICATION_ID_MAX_RESERVED + 1

    private fun displayMainActivityNotification(
        title: String,
        text: String,
        channelId: String,
        notificationId: Int
    ) {
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(text)
            .setAutoCancel(true)
            .setSound(soundUri)
            .setStyle(NotificationCompat.BigTextStyle().bigText(text))
            .setContentIntent(createMainActivityPendingIntent())
            .setPriority(NotificationCompat.PRIORITY_MAX)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    private fun createMainActivityPendingIntent(): PendingIntent {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        return PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )
    }

    private fun createTimetableChangesChannel() = context.run {
        val channelId = CHANNEL_ID_TIMETABLE_CHANGES
        context.createChannel(
            getString(R.string.channel_name_timetable_changes),
            channelId,
            IMPORTANCE_HIGH
        )
        channelId
    }

    private fun createDailyNotificationsChannel() = context.run {
        val channelId = CHANNEL_ID_DAILY_NOTIFICATIONS
        context.createChannel(
            getString(R.string.channel_name_daily_notifications),
            channelId,
            IMPORTANCE_DEFAULT
        )
        channelId
    }

    private fun Context.createChannel(title: String, id: String, importance: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(id, title, importance)
        notificationManager.createNotificationChannel(channel)
    }
}