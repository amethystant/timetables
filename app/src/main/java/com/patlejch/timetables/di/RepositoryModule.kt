package com.patlejch.timetables.di

import com.patlejch.timetables.data.repository.EventRepository
import com.patlejch.timetables.data.repository.FilterRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { EventRepository(get(), get(), get(), get()) }
    single { FilterRepository(get(), get()) }
}
