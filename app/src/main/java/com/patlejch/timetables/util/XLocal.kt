package com.patlejch.timetables.util

import com.patlejch.timetables.Config
import java.util.*

private val Int.string get() = Config.context.getString(this)

fun Date.add(days: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.DAY_OF_MONTH, days)
    return calendar.time
}
