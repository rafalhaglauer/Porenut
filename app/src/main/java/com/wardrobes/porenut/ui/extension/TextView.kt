package com.wardrobes.porenut.ui.extension

import android.widget.TextView

fun TextView.string(): String = text.toString()

fun TextView.float(): Float = text.toString().toFloatOrNull() ?: 0F