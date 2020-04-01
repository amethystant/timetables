package com.patlejch.timetables.data.repository

import com.patlejch.timetables.Config
import com.patlejch.timetables.data.database.EventDao
import com.patlejch.timetables.data.database.EventDao.Companion.fetchByDate
import com.patlejch.timetables.data.network.ApiServices
import com.patlejch.timetables.model.entity.inbound.EventsResponse
import com.patlejch.timetables.model.event.DataEvent
import com.skoumal.teanity.rxbus.RxBus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class EventRepository(
    private val eventDao: EventDao,
    private val config: Config,
    private val apiServices: ApiServices,
    private val rxBus: RxBus
) {

    suspend fun fetch() = withContext(Dispatchers.IO) {
        eventDao.fetchAll().let {
            if (it.isEmpty())
                fetchRemote()
            else
                it
        }
    }

    suspend fun fetchRemote() = withContext(Dispatchers.IO) {
        apiServices.getEvents(config.calendarUrl, config.version).await().body()!!.run {
            save()
            eventDao.fetchAll()
        }
    }

    suspend fun fetchChanges() = withContext(Dispatchers.IO) {
        apiServices.getEvents(config.calendarUrl, config.version).await().body()!!.run {
            save()
            objects
        }
    }

    suspend fun fetchByDate(date: Date) = withContext(Dispatchers.IO) {
        eventDao.fetchByDate(date)
    }

    suspend fun wipe() = withContext(Dispatchers.IO) {
        eventDao.delete()
        rxBus.post(DataEvent.EventsUpdated)
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
