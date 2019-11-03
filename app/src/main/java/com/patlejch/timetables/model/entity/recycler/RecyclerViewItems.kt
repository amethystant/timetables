package com.patlejch.timetables.model.entity.recycler

import androidx.databinding.ObservableBoolean
import com.skoumal.teanity.databinding.GenericRvItem
import com.patlejch.timetables.R
import com.skoumal.teanity.extensions.compareToSafe

class LoadingRvItem(
    val failText: String,
    val failActionText: String,
    private val failAction: () -> Unit,
    isFailed: Boolean = false
) : GenericRvItem() {

    override val layoutRes = R.layout.item_loading_more

    val failed = ObservableBoolean(isFailed)

    fun failActionClicked() = failAction()

    override fun sameAs(other: GenericRvItem) = other.compareToSafe<LoadingRvItem> { true }

    override fun contentSameAs(other: GenericRvItem) = other.compareToSafe<LoadingRvItem> { true }
}

data class EventItem(
    val id: Long,
    val title: String,
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