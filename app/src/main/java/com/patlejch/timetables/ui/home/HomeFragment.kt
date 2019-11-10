package com.patlejch.timetables.ui.home

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.patlejch.timetables.R
import com.patlejch.timetables.databinding.FragmentHomeBinding
import com.patlejch.timetables.model.base.TimetablesFragment
import com.patlejch.timetables.model.event.ViewEvents
import com.skoumal.teanity.util.Insets
import com.skoumal.teanity.viewevents.SnackbarEvent
import com.skoumal.teanity.viewevents.ViewEvent
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class HomeFragment : TimetablesFragment<HomeViewModel, FragmentHomeBinding>() {

    override val layoutRes = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModel()

    private lateinit var adapter: DailyTimetablesAdapter
    private lateinit var viewPager: ViewPager2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = binding.homeViewPager
        adapter = DailyTimetablesAdapter(viewModel.date.value, this)

        viewPager.adapter = adapter
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                viewModel.date.value = adapter.getDate(position)
            }
        })
        viewPager.setCurrentItem(adapter.half, false)
    }

    override fun onEventDispatched(event: ViewEvent) {
        super.onEventDispatched(event)

        when (event) {
            is ViewEvents.ShowDatePicker -> showDatePicker(event)
            is ViewEvents.DateSkipped -> onDateSkipped()
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

    private fun onDateSkipped() {
        try {
            adapter.apply {
                viewPager.selectDate(viewModel.date.value)
            }
        } catch (e: IndexOutOfBoundsException) {
            onEventDispatched(SnackbarEvent(R.string.home_error_cannot_display))
            viewModel.date.value = adapter.getDate(viewPager.currentItem)
        }
    }

}
