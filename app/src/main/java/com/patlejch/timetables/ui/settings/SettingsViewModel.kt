package com.patlejch.timetables.ui.settings

import com.patlejch.timetables.Config
import com.patlejch.timetables.R
import com.patlejch.timetables.data.repository.FilterRepository
import com.patlejch.timetables.model.base.TimetablesViewModel
import com.patlejch.timetables.model.entity.internal.Filter
import com.patlejch.timetables.model.event.DataEvent
import com.patlejch.timetables.model.event.ViewEvents
import com.patlejch.timetables.util.verifyUrl
import com.skoumal.teanity.extensions.addOnPropertyChangedCallback
import com.skoumal.teanity.extensions.subscribeK
import com.skoumal.teanity.rxbus.RxBus
import com.skoumal.teanity.util.BaseDiffObservableList
import com.skoumal.teanity.util.DiffObservableList
import com.skoumal.teanity.util.KObservableField
import com.skoumal.teanity.util.Observer
import java.text.DateFormat
import java.util.*

class SettingsViewModel(
    private val config: Config,
    private val filterRepository: FilterRepository,
    rxBus: RxBus
) : TimetablesViewModel() {

    val urlChanged = KObservableField(false)

    val calendarUrl = KObservableField(config.calendarUrl)
    val urlError = KObservableField(0)

    val filter = KObservableField("")
    val filters = DiffObservableList(StringDiffCallback)

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
        refreshValues()
        refreshFilters()

        rxBus.register<DataEvent.CalendarUrlUpdated>().subscribeK { refreshValues() }
        rxBus.register<DataEvent.FiltersUpdated>().subscribeK { refreshFilters() }
        rxBus.register<DataEvent.NotificationTimeUpdated>().subscribeK { refreshValues() }
        rxBus.register<DataEvent.NotificationDayBeforeUpdated>().subscribeK {
            refreshValues()
        }

        calendarUrl.addOnPropertyChangedCallback {
            config.updateCalendarUrl(it)
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

    private fun refreshValues() {
        if (urlChanged.value.not()) {
            calendarUrl.value = config.calendarUrl
        }

        config.notificationTime.apply {
            notificationHour.value = hour
            notificationMinute.value = minute
        }

        notificationDayBefore.value = config.notificationDayBefore
    }

    // section url

    fun saveUrlClicked() {
        val url = calendarUrl.value
        if (verifyUrl(url)) {
            urlError.value = 0
            urlChanged.value = false
            config.updateCalendarUrl(url)
        } else {
            urlError.value = R.string.settings_url_invalid
        }
    }

    // section filters

    private fun refreshFilters() = launch {
        runCatching {
            filters.update(filterRepository.fetch().map { it.filter })
        }.snackbarOnFailure()
    }

    private fun saveFilters() = launch {
        runCatching {
            filterRepository.save(filters.map { Filter(it) })
        }.snackbarOnFailure()
    }

    fun addFilterClicked() {
        val value = filter.value
        if (filters.contains(value).not()) {
            filters.add(value)
        }
        filter.value = ""
        saveFilters()
    }

    fun removeChipClicked(item: String) {
        filters.remove(item)
        saveFilters()
    }

    // section notifications

    fun selectNotificationTime() {
        ViewEvents.ShowTimePicker(
            notificationHour.value,
            notificationMinute.value,
            ::notificationTimeSelected
        ).publish()
    }

    private fun notificationTimeSelected(hour: Int, minute: Int) {
        notificationHour.value = hour
        notificationMinute.value = minute
    }

    private object StringDiffCallback : BaseDiffObservableList.Callback<String> {
        override fun areContentsTheSame(oldItem: String, newItem: String) = true
        override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem
    }
}