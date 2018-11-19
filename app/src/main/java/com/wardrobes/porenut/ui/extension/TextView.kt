package com.wardrobes.porenut.ui.extension

import android.widget.TextView
import com.wardrobes.porenut.ui.vo.DefaultMeasureFormatter

fun TextView.string(): String = text.toString()

fun TextView.float(): Float = DefaultMeasureFormatter.toFloat(string())