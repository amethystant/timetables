package com.patlejch.timetables.model.entity.inbound

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

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

    val startHour: Int get() = start.substringAfter("T").substring(0, 2).toInt() + 1
    val endHour: Int get() = end.substringAfter("T").substring(0, 2).toInt() + 1

}
