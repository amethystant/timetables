package com.patlejch.timetables.data.repository

import com.patlejch.timetables.data.database.FilterDao
import com.patlejch.timetables.model.entity.internal.Filter
import com.patlejch.timetables.model.event.DataEvent
import com.skoumal.teanity.rxbus.RxBus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FilterRepository(
    private val filterDao: FilterDao,
    private val rxBus: RxBus
) {

    suspend fun save(filters: List<Filter>) = withContext(Dispatchers.IO) {
        runCatching {
            filterDao.overwriteList(filters)
            rxBus.post(DataEvent.FiltersUpdated)
        }
    }

    suspend fun fetch() = withContext(Dispatchers.IO) {
        runCatching {
            filterDao.fetchAll()
        }
    }
}
