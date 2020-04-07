package com.patlejch.timetables.ui

import com.patlejch.timetables.model.base.AppViewModel
import com.patlejch.timetables.model.event.DataEvent
import com.patlejch.timetables.model.notification.NotificationManager
import com.skoumal.teanity.extensions.subscribeK
import com.skoumal.teanity.rxbus.RxBus

class MainViewModel(
    rxBus: RxBus,
    private val notificationManager: NotificationManager
) : AppViewModel() {

    init {
        rxBus.register<DataEvent.NotificationTimeUpdated>().subscribeK {
            notificationManager.scheduleDailyNotifications()
        }
    }
}
