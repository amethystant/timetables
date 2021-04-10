package com.patlejch.timetables.model.event

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import com.skoumal.teanity.viewevents.ViewEvent
import java.util.*

sealed class ViewEvents : ViewEvent() {

    class ShowDatePicker(
        date: Date,
        onSelected: OnDateSelectedListener
    ) : ViewEvents() {

        val listener = DatePickerDialog.OnDateSetListener { _, y, m, d -> onSelected(y, m, d) }
        private val calendar = Calendar.getInstance(Locale.UK)!!.apply {
            time = date
        }

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
    }

    object DateSkipped : ViewEvents()

    class ShowTimePicker(
        val hour: Int,
        val minute: Int,
        onSelected: OnTimeSelectedListener
    ) : ViewEvents() {
        val listener = TimePickerDialog.OnTimeSetListener { _, h, m -> onSelected(h, m) }
    }

    object OpenOssLicenses : ViewEvents()
}

typealias OnDateSelectedListener = (year: Int, month: Int, day: Int) -> Unit
typealias OnTimeSelectedListener = (hour: Int, minute: Int) -> Unit