package com.patlejch.timetables.data.repository

import com.patlejch.timetables.Config
import com.patlejch.timetables.data.database.EventDao
import com.patlejch.timetables.data.database.EventDao.Companion.fetchByDate
import com.patlejch.timetables.data.usecase.FetchEventsUseCase
import com.patlejch.timetables.model.entity.inbound.EventsResponse
import com.patlejch.timetables.model.event.DataEvent
import com.skoumal.teanity.rxbus.RxBus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.util.*

class EventRepository(
    private val eventDao: EventDao,
    private val config: Config,
    private val fetchEventsUseCase: FetchEventsUseCase,
    private val rxBus: RxBus
) {

    companion object {
        val mutex = Mutex()
    }

    suspend fun fetchRemote() = withContext(Dispatchers.IO) {
        mutex.withLock {
            fetchEventsUseCase().run {
                save()
                eventDao.fetchAll()
            }
        }
    }

    suspend fun fetchChanges() = withContext(Dispatchers.IO) {
        mutex.withLock {
            fetchEventsUseCase().run {
                save()
                objects
            }
        }
    }

    suspend fun fetchByDate(date: Date) = withContext(Dispatchers.IO) {
        mutex.withLock { eventDao.fetchByDate(date) }
    }

    suspend fun wipe() = withContext(Dispatchers.IO) {
        mutex.withLock {
            eventDao.delete()
            config.version = 0
            rxBus.post(DataEvent.EventsUpdated)
        }
    }

    private suspend fun EventsResponse.save() = withContext(Dispatchers.IO) {
        if (version < config.version) {
            wipe()
        }
        eventDao.insert(objects)
        config.version = version
        if (objects.isNotEmpty()) {
            rxBus.post(DataEvent.EventsUpdated)
        }
    }
}
