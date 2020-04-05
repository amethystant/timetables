package com.patlejch.timetables.ui.setup.last

import com.patlejch.timetables.model.base.AppViewModel
import com.patlejch.timetables.model.navigation.Navigation

class SetupLastViewModel : AppViewModel() {

    fun buttonClicked() = Navigation.main().publish()

}