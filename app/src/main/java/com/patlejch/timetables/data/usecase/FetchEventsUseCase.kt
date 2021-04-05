package com.patlejch.timetables.data.usecase

import com.patlejch.timetables.Config
import com.patlejch.timetables.data.network.ApiServices
import com.skoumal.teanity.api.awaitResult

class FetchEventsUseCase(
    private val apiServices: ApiServices,
    private val doMock: Boolean,
    private val config: Config
) {
    suspend operator fun invoke() = apiServices.run {
        if (doMock) getMockEvents(config.calendarUrl, config.version)
        else getEvents(config.calendarUrl, config.version)
    }.awaitResult().getOrThrow()!!
}
