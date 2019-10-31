package com.patlejch.timetables.model.global

import com.patlejch.timetables.model.entity.ui.TableDimensions
import com.skoumal.teanity.extensions.toPx

object Static {

    //todo consider generating values based on screen parameters
    val tableDimensions: TableDimensions by lazy {
        TableDimensions(56.toPx(), 128.toPx(), 9)
    }
}