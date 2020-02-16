package com.patlejch.timetables.model.base

import com.patlejch.timetables.R
import com.skoumal.teanity.viewevents.SnackbarEvent
import com.skoumal.teanity.viewmodel.LoadingViewModel

abstract class TimetablesViewModel : LoadingViewModel() {

    fun <T> Result<T>.snackbarOnFailure() = onFailure {
        SnackbarEvent(R.string.error_generic).publish()
    }

}
