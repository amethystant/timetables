package com.patlejch.timetables.ui.home.timetable

import android.util.SparseArray
import com.patlejch.timetables.BR
import com.patlejch.timetables.model.base.TimetablesViewModel
import com.patlejch.timetables.model.entity.recycler.EventItem
import com.patlejch.timetables.model.entity.recycler.TimeSlotItem
import com.patlejch.timetables.model.entity.ui.TableParams
import com.skoumal.teanity.databinding.GenericRvItem
import com.skoumal.teanity.extensions.bindingOf
import com.skoumal.teanity.extensions.diffListOf
import com.skoumal.teanity.util.DiffObservableList
import java.text.SimpleDateFormat
import java.util.*

class TimetableViewModel(val day: Date, val params: TableParams) : TimetablesViewModel() {

    val items = diffListOf<GenericRvItem>()
    val binding = bindingOf<GenericRvItem> {
        it.bindExtra(BR.viewModel, this@TimetableViewModel)
    }

    private val startingHour get() = params.startingHour
    private val endingHour get() = startingHour + params.rowCount

    val timeSlots = SparseArray<DiffObservableList<GenericRvItem>>()

    init {
        items.update((startingHour..endingHour).map { TimeSlotItem(it) })

        for (i in startingHour..endingHour) {
            timeSlots.put(i, diffListOf())
        }

        timeSlots[startingHour].add(
            EventItem(
                1,
                SimpleDateFormat("d MMMM yyyy", Locale.UK).format(day),
                startingHour
            )
        )
        timeSlots[startingHour].add(EventItem(2, "Test item 2", startingHour))
        timeSlots[startingHour + 2].add(EventItem(3, "Test item 3", startingHour + 2))
    }

}