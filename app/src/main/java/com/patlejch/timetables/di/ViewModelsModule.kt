package com.patlejch.timetables.di

import com.patlejch.timetables.ui.MainViewModel
import com.patlejch.timetables.ui.home.HomeViewModel
import com.patlejch.timetables.ui.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { MainViewModel() }
    viewModel { HomeViewModel() }
    viewModel { SettingsViewModel() }
}
