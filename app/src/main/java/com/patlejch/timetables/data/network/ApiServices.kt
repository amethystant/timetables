package com.patlejch.timetables.data.network

import com.patlejch.timetables.model.entity.inbound.EventsResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface ApiServices {

    @GET("v1/events")
    fun getEvents(
        @Query("url") url: String,
        @Query("version") currentVersion: Long
    ): Deferred<Response<EventsResponse>>

    @GET("v1/mock/events")
    fun getMockEvents(
        @Query("url") url: String,
        @Query("version") currentVersion: Long
    ): Deferred<Response<EventsResponse>>

}
