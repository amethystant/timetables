package com.patlejch.timetables.ui.home.timetable

import com.patlejch.timetables.R
import com.patlejch.timetables.databinding.FragmentTimetableBinding
import com.patlejch.timetables.model.base.TimetablesFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TimetableFragment : TimetablesFragment<TimetableViewModel, FragmentTimetableBinding>() {

    override val layoutRes = R.layout.fragment_timetable

    override val viewModel: TimetableViewModel by sharedViewModel()

}