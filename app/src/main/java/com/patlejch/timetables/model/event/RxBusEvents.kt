package com.patlejch.timetables.model.event

import com.skoumal.teanity.rxbus.RxBus

object SimpleRxBusEvents {}

sealed class DataEvent : RxBus.Event {
    object CalendarUrlUpdated : DataEvent()
    object FiltersUpdated : DataEvent()
    object NotificationTimeUpdated : DataEvent()
    object NotificationDayBeforeUpdated : DataEvent()
}