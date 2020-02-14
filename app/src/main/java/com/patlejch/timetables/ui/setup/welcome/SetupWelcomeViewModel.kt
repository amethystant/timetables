package com.patlejch.timetables.ui.setup.welcome

import com.patlejch.timetables.Config
import com.patlejch.timetables.R
import com.patlejch.timetables.model.base.TimetablesViewModel
import com.patlejch.timetables.model.navigation.Navigation
import com.patlejch.timetables.util.verifyUrl
import com.skoumal.teanity.util.KObservableField

class SetupWelcomeViewModel(val config: Config) : TimetablesViewModel() {

    val instructionsVisible = KObservableField(false)
    val url = KObservableField("")
    val error = KObservableField(0)

    fun showInstructions() {
        instructionsVisible.value = true
    }

    fun next() {
        val url = url.value
        if (verifyUrl(url)) {
            error.value = 0
            config.updateCalendarUrl(url)
            Navigation.Setup.last().publish()
        } else {
            error.value = R.string.settings_url_invalid
        }
    }

}