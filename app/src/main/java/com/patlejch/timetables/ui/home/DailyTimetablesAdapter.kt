package com.patlejch.timetables.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.patlejch.timetables.ui.home.timetable.TimetableFragment
import com.patlejch.timetables.util.fromMilisToDays
import com.patlejch.timetables.util.plus
import java.util.*

class DailyTimetablesAdapter(date: Date, fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    val dayAtHalf: Date = date

    val half: Int
        get() = itemCount / 2

    val minDate: Date
        get() = getDate(0)

    val maxDate: Date
        get() = getDate(itemCount - 1)

    override fun getItemCount() = 1000

    override fun createFragment(position: Int) = TimetableFragment.newInstance(getDate(position))

    fun getDate(position: Int) = dayAtHalf + (position - half)

    fun getPosition(date: Date) =
        (date.time.fromMilisToDays() - getDate(0).time.fromMilisToDays()).toInt()


    /**
     * @receiver must be the ViewPager2 instance this adapter is attached to
     * @param date the date to be displayed
     * @throws IndexOutOfBoundsException
     */
    fun ViewPager2.selectDate(date: Date) {
        if (date < minDate || date > maxDate) {
            throw IndexOutOfBoundsException()
        }

        setCurrentItem(getPosition(date), false)
    }

}