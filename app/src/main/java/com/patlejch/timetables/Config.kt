package com.patlejch.timetables

import com.chibatching.kotpref.KotprefModel
import com.patlejch.timetables.model.event.DataEvent
import com.skoumal.teanity.rxbus.RxBus

class Config(private val rxBus: RxBus) : KotprefModel() {
    override val kotprefName: String = "config"

    var calendarUrl by stringPref(default = "", key = "calendarUrl")
        private set

    var version by longPref(default = 0, key = "version")

    fun updateCalendarUrl(url: String) {
        calendarUrl = url
        rxBus.post(DataEvent.CalendarUrlUpdated)
    }

    fun isUrlSet() = calendarUrl.isNotBlank()
}
