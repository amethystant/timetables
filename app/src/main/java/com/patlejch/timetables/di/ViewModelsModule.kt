package com.patlejch.timetables.di

import com.patlejch.timetables.ui.MainViewModel
import com.patlejch.timetables.ui.home.HomeViewModel
import com.patlejch.timetables.ui.home.timetable.TimetableViewModel
import com.patlejch.timetables.ui.settings.SettingsViewModel
import com.patlejch.timetables.ui.setup.SetupViewModel
import com.patlejch.timetables.ui.setup.last.SetupLastViewModel
import com.patlejch.timetables.ui.setup.welcome.SetupWelcomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.*

val viewModelModules = module {
    viewModel { MainViewModel() }
    viewModel { HomeViewModel(get()) }
    viewModel { SettingsViewModel(get(), get(), get()) }
    viewModel { (date: Date) -> TimetableViewModel(date, get()) }
    viewModel { SetupViewModel() }
    viewModel { SetupWelcomeViewModel(get()) }
    viewModel { SetupLastViewModel() }
}
