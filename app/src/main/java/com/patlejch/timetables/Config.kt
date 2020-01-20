package com.patlejch.timetables

import com.chibatching.kotpref.KotprefModel
import com.patlejch.timetables.model.event.SimpleRxBusEvents
import com.skoumal.teanity.rxbus.RxBus

class Config(private val rxBus: RxBus) : KotprefModel() {
    override val kotprefName: String = "config"

    var calendarUrl by stringPref(default = "", key = "calendarUrl")
        private set

    private var _notificationTime by stringPref(default = "0900", key = "notificationTime")
    var notificationTime: NotificationTime
        private set(value) {
            _notificationTime = value.hour.toString().padStart(2, '0') +
                    value.minute.toString().padStart(2, '0')
        }
        get() {
            return NotificationTime(
                _notificationTime.substring(0, 2).toInt(),
                _notificationTime.substring(2).toInt()
            )
        }

    var notificationDayBefore by booleanPref(default = false, key = "notificationDayBefore")
        private set

    fun updateCalendarUrl(url: String) {
        calendarUrl = url
        rxBus.post(SimpleRxBusEvents.CALENDAR_URL_UPDATED)
    }

    fun updateNotificationTime(time: NotificationTime) {
        notificationTime = time
        rxBus.post(SimpleRxBusEvents.NOTIFICATION_TIME_UPDATED)
    }

    fun updateNotificationDayBefore(dayBefore: Boolean) {
        notificationDayBefore = dayBefore
        rxBus.post(SimpleRxBusEvents.NOTIFICATION_DAY_BEFORE_UPDATED)
    }

    fun isUrlSet() = calendarUrl.isNotBlank()

    data class NotificationTime(val hour: Int, val minute: Int)
}
