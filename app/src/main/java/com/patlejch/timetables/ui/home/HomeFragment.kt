package com.patlejch.timetables.ui.home

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.patlejch.timetables.R
import com.patlejch.timetables.databinding.FragmentHomeBinding
import com.patlejch.timetables.model.base.AppFragment
import com.patlejch.timetables.model.event.ViewEvents
import com.skoumal.teanity.viewevents.SnackbarEvent
import com.skoumal.teanity.viewevents.ViewEvent
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*

class HomeFragment : AppFragment<HomeViewModel, FragmentHomeBinding>() {

    override val layoutRes = R.layout.fragment_home
    override val viewModel: HomeViewModel by sharedViewModel()

    private var adapter: DailyTimetablesAdapter? = null
    private lateinit var viewPager: ViewPager2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = binding.homeViewPager
        viewPager.offscreenPageLimit = 2
        adapter = DailyTimetablesAdapter(viewModel.date.value, this).also {
            viewPager.adapter = it
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    viewModel.date.value = it.getDate(position)
                }
            })
            viewPager.setCurrentItem(it.half, false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter = null
    }

    override fun onEventDispatched(event: ViewEvent) {
        super.onEventDispatched(event)

        when (event) {
            is ViewEvents.ShowDatePicker -> showDatePicker(event)
            is ViewEvents.DateSkipped -> onDateSkipped()
        }
    }

    private fun showDatePicker(event: ViewEvents.ShowDatePicker) {
        DatePickerDialog(
            requireContext(),
            event.listener,
            event.year,
            event.month,
            event.day
        ).show()
    }

    private fun onDateSkipped() {
        try {
            adapter?.apply {
                viewPager.selectDate(viewModel.date.value)
            }
        } catch (e: IndexOutOfBoundsException) {
            onEventDispatched(SnackbarEvent(R.string.home_error_cannot_display))
            viewModel.date.value = adapter?.getDate(viewPager.currentItem) ?: Date()
        }
    }
}
