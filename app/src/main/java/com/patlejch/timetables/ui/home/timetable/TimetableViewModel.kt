package com.patlejch.timetables.ui.home.timetable

import android.util.SparseArray
import com.patlejch.timetables.BR
import com.patlejch.timetables.model.base.TimetablesViewModel
import com.patlejch.timetables.model.entity.recycler.EventItem
import com.patlejch.timetables.model.entity.recycler.TimeSlotItem
import com.patlejch.timetables.model.entity.ui.TableDimensions
import com.skoumal.teanity.databinding.GenericRvItem
import com.skoumal.teanity.extensions.bindingOf
import com.skoumal.teanity.extensions.diffListOf
import com.skoumal.teanity.extensions.toPx
import com.skoumal.teanity.util.DiffObservableList

class TimetableViewModel(val dimensions: TableDimensions) : TimetablesViewModel() {

    val items = diffListOf<GenericRvItem>()
    val binding = bindingOf<GenericRvItem> {
        it.bindExtra(BR.viewModel, this@TimetableViewModel)
    }

    val startingHour = 9 // fixme make this global

    val timeSlots = SparseArray<DiffObservableList<GenericRvItem>>()

    init {
        items.update((startingHour .. startingHour + dimensions.rowCount).map { TimeSlotItem(it) })

        timeSlots.put(startingHour, diffListOf())
        timeSlots[startingHour].add(EventItem(1, "Test item"))
    }

    fun getWidth(item: EventItem) = 150.toPx() //todo

}