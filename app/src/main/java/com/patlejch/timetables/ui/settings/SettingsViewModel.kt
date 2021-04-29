package com.patlejch.timetables.ui.settings

import com.patlejch.timetables.Config
import com.patlejch.timetables.R
import com.patlejch.timetables.data.repository.FilterRepository
import com.patlejch.timetables.data.usecase.UpdateCalendarUrlUseCase
import com.patlejch.timetables.model.base.AppViewModel
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

class SettingsViewModel(
    private val config: Config,
    private val filterRepository: FilterRepository,
    private val updateCalendarUrlUseCase: UpdateCalendarUrlUseCase,
    rxBus: RxBus
) : AppViewModel() {

    val urlChanged = KObservableField(false)

    val calendarUrl = KObservableField(config.calendarUrl)
    val urlError = KObservableField(0)

    val filter = KObservableField("")
    val filters = DiffObservableList(StringDiffCallback)

    init {
        refreshValues()
        refreshFilters()

        rxBus.register<DataEvent.CalendarUrlUpdated>().subscribeK { refreshValues() }
        rxBus.register<DataEvent.FiltersUpdated>().subscribeK { refreshFilters() }

        calendarUrl.addOnPropertyChangedCallback {
            saveUrl(it)
            urlChanged.value = true
        }
    }

    private fun refreshValues() {
        if (urlChanged.value.not()) {
            calendarUrl.value = config.calendarUrl
        }
    }

    // section url

    private fun saveUrl(url: String) = launch {
        runCatching {
            updateCalendarUrlUseCase(url)
        }
    }

    fun saveUrlClicked() {
        val url = calendarUrl.value
        if (verifyUrl(url)) {
            urlError.value = 0
            urlChanged.value = false
            saveUrl(url)
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

    // section oss libraries

    fun ossLicensesClicked() {
        ViewEvents.OpenOssLicenses.publish()
    }

    private object StringDiffCallback : BaseDiffObservableList.Callback<String> {
        override fun areContentsTheSame(oldItem: String, newItem: String) = true
        override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem
    }
}