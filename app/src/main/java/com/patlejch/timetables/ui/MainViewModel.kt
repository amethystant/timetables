package com.patlejch.timetables.ui

import com.patlejch.timetables.data.usecase.SyncUseCase
import com.patlejch.timetables.model.base.AppViewModel
import com.patlejch.timetables.model.event.DataEvent
import com.patlejch.timetables.model.notification.NotificationManager
import com.skoumal.teanity.extensions.subscribeK
import com.skoumal.teanity.rxbus.RxBus
import com.skoumal.teanity.util.KObservableField

class MainViewModel(
    rxBus: RxBus,
    private val notificationManager: NotificationManager,
    private val syncUseCase: SyncUseCase
) : AppViewModel() {

    val currentPage = KObservableField(0)

    init {
        rxBus.register<DataEvent.NotificationTimeUpdated>().subscribeK {
            scheduleNotifications()
        }
    }

    fun scheduleNotifications() = notificationManager.scheduleDailyNotifications()

    fun sync() {
        launch {
            runCatching { syncUseCase() }
        }
    }
}
