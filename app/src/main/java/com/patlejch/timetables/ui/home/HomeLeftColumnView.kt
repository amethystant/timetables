package com.patlejch.timetables.ui.home

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.patlejch.timetables.R
import java.text.SimpleDateFormat
import java.util.*

class HomeLeftColumnView : LinearLayoutCompat {

    companion object {
        val formatter = SimpleDateFormat("H:mm", Locale.UK)
    }

    var rowCount: Int = 9
        set(value) {
            field = value
            updateCount()
        }

    var startingHour: Int = 9
        set(value) {
            field = value
            updateText()
        }

    var rowHeight: Int = 40
        set(value) {
            field = value
            updateRowHeight()
            requestLayout()
        }

    private val rowsList = mutableListOf<View>()

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        orientation = VERTICAL

        val dateTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, startingHour)
            set(Calendar.MINUTE, 0)
        }

        for (i in 0 until rowCount) {
            addTextView(formatter.format(dateTime.time))
            dateTime.add(Calendar.HOUR, 1)

            if (i < rowCount - 1) {
                addSeparator()
            }
        }
    }

    private fun updateText() {
        val dateTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, startingHour)
            set(Calendar.MINUTE, 0)
        }

        for (view in rowsList) {
            if (view is TextView) {
                view.text = formatter.format(dateTime.time)
                dateTime.add(Calendar.HOUR, 1)
            }
        }
    }

    private fun updateCount() {
        val count = rowsList.filterIsInstance<TextView>().count()
        var toAdd = rowCount - count

        val dateTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, startingHour + count + 1)
            set(Calendar.MINUTE, 0)
        }

        while (toAdd != 0) {
            if (toAdd > 0) {
                if (rowsList.isNotEmpty() && rowsList.last() is TextView) {
                    addSeparator()
                }

                addTextView(formatter.format(dateTime.time))
                dateTime.add(Calendar.HOUR, 1)

                toAdd--
            }

            if (toAdd < 0) {
                var nextToRemove = getLastView()
                var removedText = false
                do {
                    if (nextToRemove is TextView)
                        removedText = true
                    nextToRemove?.let(::removeView)
                    nextToRemove = getLastView()
                } while (nextToRemove != null && !(nextToRemove is TextView && removedText))

                toAdd++
            }
        }
    }

    private fun addTextView(text: String) {
        val textView = AppCompatTextView(context)
        textView.text = text
        textView.layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            rowHeight
        )

        textView.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            resources.getDimension(R.dimen.text_small)
        )
        textView.setTextColor(resources.getColor(R.color.colorTextInactive, null))
        textView.gravity = Gravity.CENTER

        addView(textView)
        rowsList.add(textView)
    }

    private fun updateRowHeight() {
        for (view in rowsList) {
            if (view is TextView) {
                view.layoutParams = view.layoutParams.apply {
                    height = rowHeight
                }
            }
        }
    }

    private fun addSeparator() =
        View(context).apply {
            setBackgroundColor(resources.getColor(R.color.dividerGray, null))
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                2
            )
            addView(this)
            rowsList.add(this)
        }

    private fun getLastView() =
        if (childCount == 0)
            null
        else
            getChildAt(childCount - 1)
}