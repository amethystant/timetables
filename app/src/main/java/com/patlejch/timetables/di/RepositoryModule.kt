package com.patlejch.timetables.di

import com.patlejch.timetables.data.repository.FilterRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { FilterRepository(get(), get()) }
}
