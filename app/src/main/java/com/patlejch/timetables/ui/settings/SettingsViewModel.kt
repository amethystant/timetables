package com.patlejch.timetables.ui.settings

import com.patlejch.timetables.model.base.TimetablesViewModel
import com.skoumal.teanity.util.KObservableField

class SettingsViewModel : TimetablesViewModel() {

    val urlChanged = KObservableField(false)

    val calendarUrl = KObservableField("https://www.lorem-ips.um/?d=olorsitamet")

}