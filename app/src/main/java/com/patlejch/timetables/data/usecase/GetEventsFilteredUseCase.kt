package com.patlejch.timetables.data.usecase

import com.patlejch.timetables.data.repository.EventRepository
import com.patlejch.timetables.data.repository.FilterRepository
import com.patlejch.timetables.model.entity.inbound.Event
import com.patlejch.timetables.model.entity.internal.Filter
import java.util.*

class GetEventsFilteredUseCase(
    private val eventRepository: EventRepository,
    private val filterRepository: FilterRepository
) {

    suspend operator fun invoke(date: Date, filters: List<Filter>): List<Event> {
        var events = eventRepository.fetchByDate(date)
        filters.forEach { filter ->
            events = events.filter {
                it.summary.contains(filter.filter).not()
            }
        }
        return events
    }

    suspend operator fun invoke(date: Date): List<Event> = this(date, filterRepository.fetch())
}
