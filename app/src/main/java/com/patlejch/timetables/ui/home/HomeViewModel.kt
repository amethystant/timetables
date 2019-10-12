package com.patlejch.timetables.ui.home

import com.skoumal.teanity.BR
import com.skoumal.teanity.databinding.GenericRvItem
import com.patlejch.timetables.model.base.TimetablesViewModel
import com.skoumal.teanity.extensions.bindingOf
import com.skoumal.teanity.extensions.diffListOf

class HomeViewModel : TimetablesViewModel() {

    val items = diffListOf<GenericRvItem>()
    val itemBinding = bindingOf<GenericRvItem> {
        it.bindExtra(BR.viewModel, this@HomeViewModel)
    }

    override suspend fun refresh() {

    }

}
