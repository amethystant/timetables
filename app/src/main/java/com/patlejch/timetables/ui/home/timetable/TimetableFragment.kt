package com.patlejch.timetables.ui.home.timetable

import com.patlejch.timetables.R
import com.patlejch.timetables.databinding.FragmentTimetableBinding
import com.patlejch.timetables.model.base.TimetablesFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.*

class TimetableFragment : TimetablesFragment<TimetableViewModel, FragmentTimetableBinding>() {

    companion object {
        fun newInstance(date: Date) = TimetableFragment().apply { day = date }
    }

    override val layoutRes = R.layout.fragment_timetable

    override val viewModel: TimetableViewModel by viewModel {
        parametersOf(day)
    }

    private lateinit var day: Date
}
