package com.patlejch.timetables.util

import android.view.MenuItem
import android.view.View
import androidx.databinding.BindingAdapter
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.skoumal.teanity.databinding.applyTransformation
import com.patlejch.timetables.GlideApp
import com.patlejch.timetables.ui.home.HomeLeftColumnView

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
