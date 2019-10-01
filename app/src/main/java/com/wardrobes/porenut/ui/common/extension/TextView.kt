package com.wardrobes.porenut.ui.common.extension

import android.widget.TextView
import com.wardrobes.porenut.ui.common.DefaultMeasureFormatter

fun TextView.string(): String = text.toString()

fun TextView.float(): Float = DefaultMeasureFormatter.toFloat(string())