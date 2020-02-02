package com.patlejch.timetables.util

import android.content.res.ColorStateList
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.databinding.BindingAdapter
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.shape.CutCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.textfield.TextInputLayout
import com.skoumal.teanity.databinding.applyTransformation
import com.patlejch.timetables.GlideApp
import com.patlejch.timetables.R
import com.patlejch.timetables.ui.home.HomeLeftColumnView
import kotlin.math.absoluteValue

@BindingAdapter("url", "transformation", requireAll = false)
fun setImageFromUrl(view: ImageView, url: String?, transformation: Int) {
    if (url.isNullOrBlank()) {
        return
    }

    val options = RequestOptions().applyTransformation(transformation)

    GlideApp.with(view.context)
        .load(url)
        .apply(options)
        .into(view)
}

@BindingAdapter("invisible")
fun View.setInvisible(invisible: Boolean) {
    visibility = if (invisible) View.INVISIBLE else View.VISIBLE
}

@BindingAdapter("invisibleUnless")
fun View.setInvisibleUnless(invisibleUnless: Boolean) {
    visibility = if (invisibleUnless.not()) View.INVISIBLE else View.VISIBLE
}

@BindingAdapter("invisible")
fun setGroupInvisible(group: Group, invisible: Boolean) {
    group.isInvisible = invisible
    group.updatePreLayout(group.parent as ConstraintLayout)
}

@BindingAdapter("invisibleUnless")
fun setGroupInvisibleUnless(group: Group, invisibleUnless: Boolean) {
    setGroupInvisible(group, invisibleUnless.not())
}

interface OnMenuItemClickListener {
    fun onMenuItemClick(item: MenuItem): Boolean
}

@BindingAdapter("onMenuClick")
fun Toolbar.setOnMenuClickListener(listener: OnMenuItemClickListener) {
    setOnMenuItemClickListener { listener.onMenuItemClick(it) }
}

@BindingAdapter("width", "height", requireAll = false)
fun View.setDimensions(width: Int?, height: Int?) {
    val layoutParams = layoutParams

    width?.let { layoutParams.width = width }
    height?.let { layoutParams.height = height }
}

@BindingAdapter("rowCount")
fun HomeLeftColumnView.setRowCount(rowCount: Int) {
    this.rowCount = rowCount
}

@BindingAdapter("startingHour")
fun HomeLeftColumnView.setStartingHour(startingHour: Int) {
    this.startingHour = startingHour
}

@BindingAdapter("rowHeight")
fun HomeLeftColumnView.rowHeight(rowHeight: Int) {
    this.rowHeight = rowHeight
}

@BindingAdapter("minWidth")
fun View.setMinWidth(minWidth: Int) {
    minimumWidth = minWidth
}

@BindingAdapter("minHeight")
fun View.setMinHeight(minHeight: Int) {
    minimumHeight = minHeight
}

@BindingAdapter("snap")
fun RecyclerView.setSnap(on: Boolean) {
    if (on) {
        PagerSnapHelper().attachToRecyclerView(this)
    }
}

@BindingAdapter("backgroundTintId")
fun View.setBackgroundTintId(id: Int) {
    backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, id))
}

interface OnEnterListener {
    fun onEnter()
}

@BindingAdapter("onEnter")
fun EditText.setOnEnter(onEnterListener: OnEnterListener) {
    setOnEditorActionListener { _, _, _ ->
        onEnterListener.onEnter()
        true
    }
}

@BindingAdapter("error")
fun TextInputLayout.setError(error: String?) {
    setError(error)
}

@BindingAdapter("error")
fun TextInputLayout.setError(error: Int) {
    if (error == 0) {
        setError("")
    } else {
        setError(resources.getString(error))
    }
}

interface OnChipClosedListener {
    fun onChipClosed(item: String)
}

@BindingAdapter("chips", "onChipClosed", requireAll = false)
fun ChipGroup.setUp(items: List<String>, onChipClosedListener: OnChipClosedListener?) {
    val chips = children.filterIsInstance<Chip>().toList()
    val diff = chips.count() - items.count()
    if (diff > 0) {
        for (i in 0 until diff) {
            removeView(chips.elementAt(i))
        }
    } else if (diff < 0) {
        for (i in 0 until diff.absoluteValue) {
            addView(Chip(context))
        }
    }

    children.filterIsInstance<Chip>().forEachIndexed { i, chip ->
        chip.apply {
            text = items[i]
            isCloseIconVisible = true
            isCheckable = false
            isClickable = false
            shapeAppearanceModel = ShapeAppearanceModel.Builder()
                .setAllCorners(CutCornerTreatment())
                .setAllCornerSizes(resources.getDimension(R.dimen.radius_generic))
                .build()
            setOnCloseIconClickListener {
                onChipClosedListener?.onChipClosed(items[i])
            }
        }
    }
}