package com.patlejch.timetables.di

import com.patlejch.timetables.data.usecase.*
import org.koin.core.qualifier.named
import org.koin.dsl.module

val useCaseModule = module {
    factory { UpdateCalendarUrlUseCase(get(), get()) }
    factory { GetEventsFilteredUseCase(get(), get()) }
    factory { SyncUseCase(get(), get()) }
    factory { DisplayChangesNotificationUseCase(get()) }
    factory { FetchEventsUseCase(get(), get(named(DI_DO_MOCK)), get()) }
}
