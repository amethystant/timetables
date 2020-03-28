package com.patlejch.timetables.data.repository

import com.patlejch.timetables.Config
import com.patlejch.timetables.data.database.EventDao
import com.patlejch.timetables.data.database.EventDao.Companion.fetchByDate
import com.patlejch.timetables.data.network.ApiServices
import com.patlejch.timetables.model.event.DataEvent
import com.patlejch.timetables.util.dbFormat
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
        apiServices.getEvents(config.calendarUrl, config.version).await().body()!!.let {
            if (it.version < config.version) {
                eventDao.delete()
            }
            eventDao.insert(it.objects)
            config.version = it.version
            rxBus.post(DataEvent.EventsUpdated)
            it.objects
        }
    }

    suspend fun fetchByDate(date: Date) = withContext(Dispatchers.IO) {
        eventDao.fetchByDate(date)
    }

    suspend fun fetchByDateRemote(date: Date) = fetchRemote().filter {
        it.start.startsWith(date.dbFormat())
    }
}
