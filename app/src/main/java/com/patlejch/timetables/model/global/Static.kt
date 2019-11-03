package com.patlejch.timetables.model.global

import com.patlejch.timetables.model.entity.ui.TableParams
import com.skoumal.teanity.extensions.toPx

object Static {

    //todo consider generating values based on screen parameters
    val tableParams: TableParams by lazy {
        TableParams(56.toPx(), 128.toPx(), 9, 9)
    }
}