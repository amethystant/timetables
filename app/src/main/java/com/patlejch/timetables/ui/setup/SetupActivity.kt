package com.patlejch.timetables.ui.setup

import com.patlejch.timetables.R
import com.patlejch.timetables.databinding.ActivitySetupBinding
import com.patlejch.timetables.model.base.TimetablesActivity
import com.skoumal.teanity.util.Insets

import org.koin.androidx.viewmodel.ext.android.viewModel

class SetupActivity : TimetablesActivity<SetupViewModel, ActivitySetupBinding>() {

    override val layoutRes = R.layout.activity_setup
    override val viewModel: SetupViewModel by viewModel()

    override fun consumeSystemWindowInsets(left: Int, top: Int, right: Int, bottom: Int) =
        Insets(top = top, bottom = bottom)

}