package com.patlejch.timetables.ui.settings

import android.webkit.URLUtil
import com.google.android.material.snackbar.Snackbar
import com.patlejch.timetables.Config
import com.patlejch.timetables.R
import com.patlejch.timetables.model.base.TimetablesViewModel
import com.patlejch.timetables.model.event.SimpleRxBusEvents
import com.patlejch.timetables.model.event.ViewEvents
import com.skoumal.teanity.extensions.addOnPropertyChangedCallback
import com.skoumal.teanity.extensions.subscribeK
import com.skoumal.teanity.rxbus.RxBus
import com.skoumal.teanity.util.KObservableField
import com.skoumal.teanity.util.Observer
import com.skoumal.teanity.viewevents.SnackbarEvent
import java.text.DateFormat
import java.util.*

class SettingsViewModel(
    private val config: Config,
    rxBus: RxBus
) : TimetablesViewModel() {

    val urlChanged = KObservableField(false)

    val calendarUrl = KObservableField(config.calendarUrl)

    private val notificationHour = KObservableField(0)
    private val notificationMinute = KObservableField(0)
    val notificationTimeFormatted = Observer(notificationHour, notificationMinute) {
        return@Observer Calendar.getInstance().run {
            set(Calendar.HOUR_OF_DAY, notificationHour.value)
            set(Calendar.MINUTE, notificationMinute.value)
            DateFormat.getTimeInstance(DateFormat.SHORT).format(time)
        }
    }

    val notificationDayBefore = KObservableField(config.notificationDayBefore)

    init {
        updateValues()

        rxBus.register(SimpleRxBusEvents.CALENDAR_URL_UPDATED).subscribeK { updateValues() }
        rxBus.register(SimpleRxBusEvents.NOTIFICATION_TIME_UPDATED).subscribeK { updateValues() }
        rxBus.register(SimpleRxBusEvents.NOTIFICATION_DAY_BEFORE_UPDATED).subscribeK {
            updateValues()
        }

        calendarUrl.addOnPropertyChangedCallback {
            //config.updateCalendarUrl(it)
            urlChanged.value = true
        }

        val timeObserver: (Int) -> Unit = {
            config.updateNotificationTime(
                Config.NotificationTime(notificationHour.value, notificationMinute.value)
            )
        }
        notificationHour.addOnPropertyChangedCallback(callback = timeObserver)
        notificationMinute.addOnPropertyChangedCallback(callback = timeObserver)

        notificationDayBefore.addOnPropertyChangedCallback(
            callback = config::updateNotificationDayBefore
        )
    }

    private fun updateValues() {
        if (urlChanged.value.not()) {
            calendarUrl.value = config.calendarUrl
        }

        config.notificationTime.apply {
            notificationHour.value = hour
            notificationMinute.value = minute
        }

        notificationDayBefore.value = config.notificationDayBefore
    }

    fun saveUrlClicked() {
        val url = calendarUrl.value
        if (urlValid(url)) {
            urlChanged.value = false
            config.updateCalendarUrl(url)
        } else {
            SnackbarEvent(R.string.settings_url_invalid, Snackbar.LENGTH_LONG).publish()
        }
    }

    private fun urlValid(url: String) = URLUtil.isValidUrl(url)

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