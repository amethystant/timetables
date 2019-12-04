package com.patlejch.timetables.model.event

import android.app.TimePickerDialog
import com.skoumal.teanity.viewevents.ViewEvent
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.util.*

sealed class ViewEvents : ViewEvent() {

    class ShowDatePicker(
        date: Date,
        onSelected: OnDateSelectedListener,
        val minDate: Calendar? = null,
        val maxDate: Calendar? = null
    ) : ViewEvents() {

        val listener = DatePickerDialog.OnDateSetListener { _, y, m, d -> onSelected(y, m, d) }
        val initialDate = Calendar.getInstance(Locale.UK)!!.apply {
            time = date
        }
    }

    object DateSkipped : ViewEvents()

    class ShowTimePicker(
        val hour: Int,
        val minute: Int,
        onSelected: OnTimeSelectedListener
    ) : ViewEvents() {
        val listener = TimePickerDialog.OnTimeSetListener { _, h, m -> onSelected(h, m) }
    }
}

typealias OnDateSelectedListener = (year: Int, month: Int, day: Int) -> Unit
typealias OnTimeSelectedListener = (hour: Int, minute: Int) -> Unit