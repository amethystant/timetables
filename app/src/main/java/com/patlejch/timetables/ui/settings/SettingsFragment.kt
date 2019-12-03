package com.patlejch.timetables.ui.settings

import com.patlejch.timetables.R
import com.patlejch.timetables.databinding.FragmentSettingsBinding
import com.patlejch.timetables.model.base.TimetablesFragment
import com.skoumal.teanity.util.Insets
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SettingsFragment : TimetablesFragment<SettingsViewModel, FragmentSettingsBinding>() {

    override val layoutRes = R.layout.fragment_settings

    override val viewModel: SettingsViewModel by sharedViewModel()

    override fun consumeSystemWindowInsets(left: Int, top: Int, right: Int, bottom: Int) =
        Insets(top = top)

}