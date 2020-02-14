package com.patlejch.timetables.ui.setup.welcome

import com.patlejch.timetables.R
import com.patlejch.timetables.databinding.FragmentSetupWelcomeBinding
import com.patlejch.timetables.model.base.TimetablesFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SetupWelcomeFragment :
    TimetablesFragment<SetupWelcomeViewModel, FragmentSetupWelcomeBinding>() {

    override val layoutRes = R.layout.fragment_setup_welcome
    override val viewModel: SetupWelcomeViewModel by viewModel()

}