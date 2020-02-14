package com.patlejch.timetables.ui.setup.last

import com.patlejch.timetables.model.base.TimetablesViewModel
import com.patlejch.timetables.model.navigation.Navigation

class SetupLastViewModel : TimetablesViewModel() {

    fun buttonClicked() = Navigation.main().publish()

}