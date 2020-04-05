package com.patlejch.timetables.ui.setup.last

import com.patlejch.timetables.R
import com.patlejch.timetables.databinding.FragmentSetupLastBinding
import com.patlejch.timetables.model.base.AppFragment

import org.koin.androidx.viewmodel.ext.android.viewModel

class SetupLastFragment : AppFragment<SetupLastViewModel, FragmentSetupLastBinding>() {

    override val layoutRes = R.layout.fragment_setup_last
    override val viewModel: SetupLastViewModel by viewModel()

}
