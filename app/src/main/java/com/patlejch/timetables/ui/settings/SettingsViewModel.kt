package com.patlejch.timetables.ui.settings

import com.patlejch.timetables.model.base.TimetablesViewModel
import com.patlejch.timetables.model.event.ViewEvents
import com.skoumal.teanity.util.KObservableField
import com.skoumal.teanity.util.Observer
import java.text.DateFormat
import java.util.*

class SettingsViewModel : TimetablesViewModel() {

    val urlChanged = KObservableField(false)

    val calendarUrl = KObservableField("https://www.lorem-ips.um/?d=olorsitamet")

    private val notificationHour = KObservableField(22)
    private val notificationMinute = KObservableField(0)
    val notificationTimeFormatted = Observer(notificationHour, notificationMinute) {
        return@Observer Calendar.getInstance().run {
            set(Calendar.HOUR_OF_DAY, notificationHour.value)
            set(Calendar.MINUTE, notificationMinute.value)
            DateFormat.getTimeInstance(DateFormat.SHORT).format(time)
        }
    }

    fun selectNotificationTime() {
        ViewEvents.ShowTimePicker(
            notificationHour.value,
            notificationMinute.value,
            ::onNotificationTimeSelected
        ).publish()
    }

    private fun onNotificationTimeSelected(hour: Int, minute: Int) {
        notificationHour.value = hour
        notificationMinute.value = minute
    }
}