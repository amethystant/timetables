package com.patlejch.timetables.di

import com.patlejch.timetables.ui.MainViewModel
import com.patlejch.timetables.ui.home.HomeViewModel
import com.patlejch.timetables.ui.home.timetable.TimetableViewModel
import com.patlejch.timetables.ui.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.*

val viewModelModules = module {
    viewModel { MainViewModel() }
    viewModel { HomeViewModel(get()) }
    viewModel { SettingsViewModel(get(), get()) }
    viewModel { (date: Date) -> TimetableViewModel(date, get()) }
}
