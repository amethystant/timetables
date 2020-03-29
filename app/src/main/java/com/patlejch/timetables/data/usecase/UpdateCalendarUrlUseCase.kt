package com.patlejch.timetables.data.usecase

import com.patlejch.timetables.Config
import com.patlejch.timetables.data.repository.EventRepository

class UpdateCalendarUrlUseCase(
    private val eventRepository: EventRepository,
    private val config: Config
) {

    suspend operator fun invoke(url: String) {
        config.updateCalendarUrl(url)
        config.version = 0
        eventRepository.wipe()
        eventRepository.fetchRemote()
    }
}
