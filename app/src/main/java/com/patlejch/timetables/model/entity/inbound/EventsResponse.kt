package com.patlejch.timetables.model.entity.inbound

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EventsResponse(
    val objects: List<Event>,
    val version: Long
)
