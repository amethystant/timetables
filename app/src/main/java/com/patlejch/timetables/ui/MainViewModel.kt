package com.patlejch.timetables.ui

import com.patlejch.timetables.data.usecase.SyncUseCase
import com.patlejch.timetables.model.base.AppViewModel
import com.skoumal.teanity.util.KObservableField

class MainViewModel(private val syncUseCase: SyncUseCase) : AppViewModel() {

    val currentPage = KObservableField(0)

    fun sync() {
        launch {
            runCatching { syncUseCase() }
        }
    }
}
