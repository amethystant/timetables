package com.patlejch.timetables.util

import com.patlejch.timetables.Config
import java.util.*

private val Int.string get() = Config.context.getString(this)

operator fun Date.plus(days: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.DAY_OF_MONTH, days)
    return calendar.time
}

operator fun Date.minus(days: Int) = plus(-days)

fun Long.fromMilisToDays() = this / 1000 / 3600 / 24