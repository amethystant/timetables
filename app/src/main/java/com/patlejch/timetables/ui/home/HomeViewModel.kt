package com.patlejch.timetables.ui.home

import android.view.MenuItem
import com.patlejch.timetables.R
import com.skoumal.teanity.BR
import com.skoumal.teanity.databinding.GenericRvItem
import com.patlejch.timetables.model.base.TimetablesViewModel
import com.skoumal.teanity.extensions.bindingOf
import com.skoumal.teanity.extensions.diffListOf
import com.skoumal.teanity.util.KObservableField

class HomeViewModel : TimetablesViewModel() {

    val items = diffListOf<GenericRvItem>()
    val itemBinding = bindingOf<GenericRvItem> {
        it.bindExtra(BR.viewModel, this@HomeViewModel)
    }

    val dateFormatted = KObservableField("16 October 2019")

    override suspend fun refresh() {

    }

    fun onMenuItemClicked(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.today -> goToToday()
            R.id.select_date -> selectDate()
        }
        return true
    }

    private fun goToToday() {

    }

    private fun selectDate() {

    }

}
