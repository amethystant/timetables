package com.patlejch.timetables.data.usecase

import com.patlejch.timetables.model.entity.inbound.Event
import com.patlejch.timetables.model.notification.NotificationManager

class DisplayChangesNotificationUseCase(
    private val notificationManager: NotificationManager
) {

    operator fun invoke(changes: List<Event>) {
        if (changes.count() == 0)
            return

        if (changes.count() < 5) {
            changes.forEach { notificationManager.displayTimetableChangeNotification(it) }
            return
        }

        val deleted = changes.count { it.deleted }
        val new = changes.count() - deleted
        notificationManager.displayTimetableChangesNotification(new, deleted)
    }
}
