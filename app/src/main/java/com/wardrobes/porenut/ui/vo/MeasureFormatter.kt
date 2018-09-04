package com.wardrobes.porenut.ui.vo

import java.text.NumberFormat
import java.util.*

interface MeasureFormatter {

    fun format(measurement: Float): String

    fun toFloat(measurement: String): Float
}

object DefaultMeasureFormatter : MeasureFormatter {

    override fun format(measurement: Float): String = String.format(Locale.getDefault(), "%.1f", measurement)

    override fun toFloat(measurement: String): Float = NumberFormat.getInstance().parse(measurement).toFloat()
}