package com.wardrobes.porenut.ui.common.extension

import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setDivider(@DrawableRes drawableResId: Int) {
    addItemDecoration(
        DividerItemDecoration(context, LinearLayoutManager.VERTICAL).apply {
            context.getDrawable(drawableResId)?.also { setDrawable(it) }
        }
    )
}