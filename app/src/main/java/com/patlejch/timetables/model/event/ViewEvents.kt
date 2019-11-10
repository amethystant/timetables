package com.patlejch.timetables.model.event

import com.skoumal.teanity.viewevents.ViewEvent
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.util.*

sealed class ViewEvents : ViewEvent() {

    class ShowDatePicker(
        date: Date,
        onSelected: OnSelectedListener,
        val minDate: Calendar? = null,
        val maxDate: Calendar? = null
    ) : ViewEvents() {

        val listener = DatePickerDialog.OnDateSetListener { _, y, m, d -> onSelected(y, m, d) }
        val initialDate = Calendar.getInstance(Locale.UK)!!.apply {
            time = date
        }

    }

    object DateSkipped : ViewEvents()

}

typealias OnSelectedListener = (year: Int, month: Int, day: Int) -> Unit