package com.patlejch.timetables.util

import java.util.*

operator fun Date.plus(days: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.DAY_OF_MONTH, days)
    return calendar.time
}

operator fun Date.minus(days: Int) = plus(-days)

fun Long.fromMilisToDays() = this / 1000 / 3600 / 24