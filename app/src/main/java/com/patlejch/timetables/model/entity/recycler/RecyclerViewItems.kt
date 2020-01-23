package com.patlejch.timetables.model.entity.recycler

import com.skoumal.teanity.databinding.GenericRvItem
import com.patlejch.timetables.R
import com.skoumal.teanity.extensions.compareToSafe

data class EventItem(
    val id: Long,
    val title: String,
    val venue: String,
    val titleLong: String?,
    val hour: Int
) : GenericRvItem() {

    override val layoutRes = R.layout.item_event

    override fun contentSameAs(other: GenericRvItem) = this == other

    override fun sameAs(other: GenericRvItem) = compareToSafe<EventItem> { id == it.id }
}

data class TimeSlotItem(
    val hour: Int
) : GenericRvItem() {

    override val layoutRes = R.layout.item_time_slot

    override fun contentSameAs(other: GenericRvItem) = true

    override fun sameAs(other: GenericRvItem) = compareToSafe<TimeSlotItem> { hour == it.hour }
}