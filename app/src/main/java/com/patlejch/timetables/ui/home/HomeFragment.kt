package com.patlejch.timetables.ui.home

import com.patlejch.timetables.R
import com.patlejch.timetables.databinding.FragmentHomeBinding
import com.patlejch.timetables.model.base.TimetablesFragment
import com.patlejch.timetables.model.event.ViewEvents
import com.skoumal.teanity.util.Insets
import com.skoumal.teanity.viewevents.ViewEvent
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class HomeFragment : TimetablesFragment<HomeViewModel, FragmentHomeBinding>() {

    override val layoutRes: Int = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModel()

    override fun onEventDispatched(event: ViewEvent) {
        super.onEventDispatched(event)

        if (event is ViewEvents.ShowDatePicker) {
            showDatePicker(event)
        }
    }

    override fun consumeSystemWindowInsets(left: Int, top: Int, right: Int, bottom: Int) =
        Insets(top = top)

    private fun showDatePicker(event: ViewEvents.ShowDatePicker) {
        DatePickerDialog.newInstance(event.listener, event.initialDate).apply {
            locale = Locale.UK
            event.minDate?.let { minDate = it }
            event.maxDate?.let { maxDate = it }
            show(this@HomeFragment.requireActivity().supportFragmentManager, "DatePicker")
        }
    }

}
