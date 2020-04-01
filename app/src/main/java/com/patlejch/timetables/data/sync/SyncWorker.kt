package com.patlejch.timetables.data.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.patlejch.timetables.data.repository.EventRepository
import com.patlejch.timetables.model.event.DataEvent
import com.skoumal.teanity.rxbus.RxBus
import org.koin.core.KoinComponent
import org.koin.core.inject

class SyncWorker(context: Context, parameters: WorkerParameters) :
    CoroutineWorker(context, parameters),
    KoinComponent {

    companion object {
        const val NAME = "SyncWorker"
        const val NAME_ONE_TIME = "SyncWorker_onetime"
    }

    private val eventRepository: EventRepository by inject()

    private val rxBus: RxBus by inject()

    override suspend fun doWork(): Result {
        runCatching {
            val changes = eventRepository.fetchChanges()
            // todo notify of changes
        }

        rxBus.post(DataEvent.EventsUpdated)
        return Result.success()
    }

}
