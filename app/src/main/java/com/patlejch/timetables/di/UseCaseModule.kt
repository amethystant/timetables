package com.patlejch.timetables.di

import com.patlejch.timetables.data.usecase.GetEventsFilteredUseCase
import com.patlejch.timetables.data.usecase.UpdateCalendarUrlUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { UpdateCalendarUrlUseCase(get(), get()) }
    single { GetEventsFilteredUseCase(get(), get()) }
}
