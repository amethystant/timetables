package com.patlejch.timetables.ui.home.timetable

import android.util.SparseArray
import com.patlejch.timetables.BR
import com.patlejch.timetables.data.repository.EventRepository
import com.patlejch.timetables.model.base.TimetablesViewModel
import com.patlejch.timetables.model.entity.inbound.Event
import com.patlejch.timetables.model.entity.recycler.EventItem
import com.patlejch.timetables.model.entity.recycler.TimeSlotItem
import com.patlejch.timetables.model.entity.ui.TableParams
import com.patlejch.timetables.model.event.DataEvent
import com.patlejch.timetables.model.global.Static
import com.skoumal.teanity.databinding.GenericRvItem
import com.skoumal.teanity.extensions.bindingOf
import com.skoumal.teanity.extensions.diffListOf
import com.skoumal.teanity.extensions.subscribeK
import com.skoumal.teanity.rxbus.RxBus
import com.skoumal.teanity.util.DiffObservableList
import com.skoumal.teanity.util.KObservableField
import java.util.*

class TimetableViewModel(
    private val day: Date,
    val params: TableParams,
    private val eventRepository: EventRepository,
    rxBus: RxBus
) : TimetablesViewModel() {

    val items = diffListOf<GenericRvItem>()
    val binding = bindingOf<GenericRvItem> {
        it.bindExtra(BR.viewModel, this@TimetableViewModel)
    }

    private val startingHour get() = params.startingHour
    private val endingHour get() = startingHour + params.rowCount - 1

    private val colors = mutableMapOf<String, Int>()

    val timeSlots = SparseArray<DiffObservableList<GenericRvItem>>()

    val refreshing = KObservableField(false)

    init {
        items.update((startingHour..endingHour).map { TimeSlotItem(it) })

        for (i in startingHour..endingHour) {
            timeSlots.put(i, diffListOf())
        }

        refreshLocal()

        // todo:
        // - apply filters
        // - swipe refresh layout not working (maybe delete it altogether and replace with refreshing in onResume in parent fragment
        // - missing view on empty list
        // - https
        
        rxBus.register<DataEvent.EventsUpdated>().subscribeK {
            refreshLocal()
        }
    }

    fun refreshRemote() = launch {
        runCatching {
            eventRepository.fetchByDateRemote(day).replaceList()
        }.snackbarOnFailure()
        refreshing.value = false
    }

    private fun refreshLocal() = launch {
        runCatching {
            eventRepository.fetchByDate(day).replaceList()
        }.snackbarOnFailure()
        refreshing.value = false
    }

    private fun clearSlots() = (startingHour..endingHour).forEach { timeSlots[it]?.clear() }

    private fun List<Event>.replaceList() {
        clearSlots()
        forEach {
            val hours = (it.endHour - it.startHour).let { if (it == 0) 1 else it }
            for (h in 0 until hours) {
                timeSlots[it.startHour + h]?.add(
                    EventItem(
                        it.id,
                        it.summary,
                        it.location,
                        it.startHour
                    )
                )
            }
        }
    }

    fun getColor(item: EventItem): Int {
        colors[item.title]?.let { return it }
        val color = Static.randomItemColor(item.title)
        colors[item.title] = color
        return color
    }
}
