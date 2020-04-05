package com.patlejch.timetables.model.entity.inbound

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.patlejch.timetables.util.calendarBritish
import com.squareup.moshi.JsonClass
import java.util.*

@Entity(tableName = "event")
@JsonClass(generateAdapter = true)
data class Event(
    @PrimaryKey val id: Long,
    val start: String,
    val end: String,
    val summary: String,
    val location: String,
    val deleted: Boolean
) {
    @delegate:Ignore
    val startDateBritish: Calendar by lazy { start.calendarBritish() }
    @delegate:Ignore
    val endDateBritish: Calendar by lazy { end.calendarBritish() }
    @delegate:Ignore
    val startHour: Int by lazy { startDateBritish.get(Calendar.HOUR_OF_DAY) }
    @delegate:Ignore
    val endHour: Int by lazy { endDateBritish.get(Calendar.HOUR_OF_DAY) }
}
