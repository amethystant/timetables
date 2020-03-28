package com.patlejch.timetables.data.database

import androidx.room.Dao
import androidx.room.Query
import com.patlejch.timetables.model.entity.inbound.Event
import com.patlejch.timetables.util.dbFormat
import com.skoumal.teanity.database.BaseDao
import java.util.*

@Dao
interface EventDao : BaseDao<Event> {

    companion object {
        suspend fun EventDao.fetchByDate(date: Date): List<Event> =
            fetchByDateString(date.dbFormat())
    }

    @Query("select * from event")
    suspend fun fetchAll(): List<Event>

    @Query("select * from event where start like :dateString || '%'")
    suspend fun fetchByDateString(dateString: String): List<Event>

    @Query("delete from event")
    suspend fun delete()
}
