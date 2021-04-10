package com.patlejch.timetables.ui.home

import android.view.MenuItem
import com.patlejch.timetables.R
import com.patlejch.timetables.model.base.AppViewModel
import com.patlejch.timetables.model.entity.ui.TableParams
import com.patlejch.timetables.model.event.ViewEvents
import com.patlejch.timetables.util.minus
import com.patlejch.timetables.util.plus
import com.skoumal.teanity.util.KObservableField
import com.skoumal.teanity.util.Observer
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel(
    val params: TableParams
) : AppViewModel() {

    companion object {
        val dateFormat = SimpleDateFormat("EEE d MMMM yyyy", Locale.UK)
    }

    val startingHour get() = params.startingHour

    val date = KObservableField(Date())
    val dateFormatted = Observer(date) { dateFormat.format(date.value) }

    fun nextDay() {
        date.value = date.value + 1
    }

    fun previousDay() {
        date.value = date.value - 1
    }

    private fun goToToday() {
        date.value = Date()
        ViewEvents.DateSkipped.publish()
    }

    private fun selectDate() {
        ViewEvents.ShowDatePicker(
            date.value,
            this::onDateSelected
        ).publish()
    }

    private fun onDateSelected(year: Int, month: Int, day: Int) {
        date.value = Calendar.getInstance().apply {
            set(year, month, day)
        }.time
        ViewEvents.DateSkipped.publish()
    }

    fun onMenuItemClicked(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.today -> goToToday()
            R.id.select_date -> selectDate()
        }
        return true
    }
}
