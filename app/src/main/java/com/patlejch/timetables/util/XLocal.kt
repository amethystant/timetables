package com.patlejch.timetables.util

import android.content.res.Resources
import android.webkit.URLUtil
import com.patlejch.timetables.Constants
import com.patlejch.timetables.R
import java.text.SimpleDateFormat
import java.util.*

operator fun Date.plus(days: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.DAY_OF_MONTH, days)
    return calendar.time
}

operator fun Date.minus(days: Int) = plus(-days)

fun Long.fromMillisToDays() = this / 1000 / 3600 / 24

fun verifyUrl(url: String) = URLUtil.isValidUrl(url)

private val dbFormatDay = SimpleDateFormat(Constants.DB_FORMAT_DAY, Locale.UK).apply {
    timeZone = TimeZone.getTimeZone("UTC")
}

private val dbFormatWhole = SimpleDateFormat(Constants.DB_FORMAT_WHOLE, Locale.UK).apply {
    timeZone = TimeZone.getTimeZone("UTC")
}

fun Date.dbFormatDay() = dbFormatDay.format(this)

fun String.dateUtc() = dbFormatWhole.parse(this)

val timeZoneBritish: TimeZone = TimeZone.getTimeZone("Europe/London")

fun String.calendarBritish() = Calendar.getInstance().apply {
    time = dateUtc() ?: throw IllegalArgumentException("Unable to parse date.")
    timeZone = timeZoneBritish
}

private val notificationFormatDay = SimpleDateFormat("d/M", Locale.UK).apply {
    timeZone = timeZoneBritish
}

private val notificationFormatTime = SimpleDateFormat("HH:mm", Locale.UK).apply {
    timeZone = timeZoneBritish
}

infix fun Calendar.sameDayAs(other: Calendar) =
    get(Calendar.DAY_OF_MONTH) == other.get(Calendar.DAY_OF_MONTH)
            && get(Calendar.MONTH) == other.get(Calendar.MONTH)
            && get(Calendar.YEAR) == other.get(Calendar.YEAR)

fun Calendar.notificationFormat(resources: Resources): String {
    val day = Calendar.getInstance(timeZoneBritish).let {
        if (this sameDayAs it)
            resources.getString(R.string.today)
        else
            notificationFormatDay.format(this)
    }

    val time = notificationFormatTime.format(this)

    return "$day, $time"
}

fun Calendar.formatTimeOnly() = notificationFormatTime.format(this.time.time)