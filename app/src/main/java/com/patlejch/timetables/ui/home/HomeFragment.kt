package com.patlejch.timetables.ui.home

import com.patlejch.timetables.R
import com.patlejch.timetables.databinding.FragmentHomeBinding
import com.patlejch.timetables.model.base.TimetablesFragment
import com.skoumal.teanity.util.Insets
import com.skoumal.teanity.view.TeanityFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : TimetablesFragment<HomeViewModel, FragmentHomeBinding>() {

    override val layoutRes: Int = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModel()

    override fun consumeSystemWindowInsets(left: Int, top: Int, right: Int, bottom: Int) =
        Insets(top = top)

}
