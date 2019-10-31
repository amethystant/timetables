package com.patlejch.timetables.ui.home

import android.view.MenuItem
import com.patlejch.timetables.R
import com.patlejch.timetables.model.base.TimetablesViewModel
import com.patlejch.timetables.model.entity.ui.TableDimensions
import com.patlejch.timetables.model.event.ViewEvents
import com.patlejch.timetables.util.add
import com.skoumal.teanity.util.KObservableField
import com.skoumal.teanity.util.Observer
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel(val dimensions: TableDimensions) : TimetablesViewModel() {

    companion object {
        val dateFormat = SimpleDateFormat("d MMMM yyyy", Locale.UK)
    }

    val startingHour = 9

    val date = KObservableField(Date())
    val dateFormatted = Observer(date) { dateFormat.format(date.value) }

    fun nextDay() {
        date.value = date.value.add(1)
    }

    fun previousDay() {
        date.value = date.value.add(-1)
    }

    private fun goToToday() {
        date.value = Date()
    }

    private fun selectDate() {
        ViewEvents.ShowDatePicker(
            date.value,
            this::onDateSelected,
            Calendar.getInstance()
        ).publish()
    }

    private fun onDateSelected(year: Int, month: Int, day: Int) {
        date.value = Calendar.getInstance().apply {
            set(year, month, day)
        }.time
    }

    fun onMenuItemClicked(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.today -> goToToday()
            R.id.select_date -> selectDate()
        }
        return true
    }
}
