package com.patlejch.timetables.ui.home.timetable

import android.util.SparseArray
import com.patlejch.timetables.BR
import com.patlejch.timetables.data.repository.FilterRepository
import com.patlejch.timetables.data.usecase.GetEventsFilteredUseCase
import com.patlejch.timetables.model.base.AppViewModel
import com.patlejch.timetables.model.entity.inbound.Event
import com.patlejch.timetables.model.entity.internal.Filter
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
import kotlin.collections.ArrayList

class TimetableViewModel(
    private val day: Date,
    val params: TableParams,
    private val getEventsFilteredUseCase: GetEventsFilteredUseCase,
    private val filterRepository: FilterRepository,
    rxBus: RxBus
) : AppViewModel() {

    val items = diffListOf<GenericRvItem>()
    val binding = bindingOf<GenericRvItem> {
        it.bindExtra(BR.viewModel, this@TimetableViewModel)
    }

    val empty = KObservableField(true)

    private val startingHour get() = params.startingHour
    private val endingHour get() = startingHour + params.rowCount - 1

    private val colors = mutableMapOf<String, Int>()
    private var filters = listOf<Filter>()

    val timeSlots = SparseArray<DiffObservableList<GenericRvItem>>()

    val refreshing = KObservableField(true)

    init {
        items.update((startingHour..endingHour).map { TimeSlotItem(it) })

        for (i in startingHour..endingHour) {
            timeSlots.put(i, diffListOf())
        }

        filtersUpdated()

        rxBus.register<DataEvent.FiltersUpdated>().subscribeK {
            filtersUpdated()
        }

        rxBus.register<DataEvent.EventsUpdated>().subscribeK {
            refreshLocal()
        }
    }

    private fun filtersUpdated() = launch {
        runCatching {
            filters = filterRepository.fetch()
            refreshLocal()
        }
    }

    private fun refreshLocal() = launch {
        refreshing.value = true
        runCatching {
            getEventsFilteredUseCase(day, filters).replaceList()
        }.snackbarOnFailure()
        refreshing.value = false
    }

    private fun List<Event>.replaceList() {
        empty.value = isEmpty()
        val newTimeSlots = SparseArray<ArrayList<GenericRvItem>>()
        (startingHour..endingHour).forEach { newTimeSlots.put(it, arrayListOf()) }

        forEach {
            val hours = (it.endHour - it.startHour).let { if (it == 0) 1 else it }
            for (h in 0 until hours) {
                newTimeSlots[it.startHour + h]?.add(
                    EventItem(
                        it.id,
                        it.summary,
                        it.location,
                        it.startHour
                    )
                )
            }
        }

        (startingHour..endingHour).forEach { timeSlots[it].update(newTimeSlots[it]) }
    }

    fun getColor(item: EventItem): Int {
        colors[item.title]?.let { return it }
        val color = Static.randomItemColor(item.title)
        colors[item.title] = color
        return color
    }
}
