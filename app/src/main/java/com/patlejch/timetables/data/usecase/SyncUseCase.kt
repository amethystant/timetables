package com.patlejch.timetables.data.usecase

import com.patlejch.timetables.data.repository.EventRepository

class SyncUseCase(
    private val eventRepository: EventRepository,
    private val displayChangesNotificationUseCase: DisplayChangesNotificationUseCase
) {

    suspend operator fun invoke() {
        displayChangesNotificationUseCase(eventRepository.fetchChanges())
    }
}
