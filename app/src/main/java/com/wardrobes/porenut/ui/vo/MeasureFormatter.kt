package com.wardrobes.porenut.ui.vo

import java.text.NumberFormat
import java.util.*

interface MeasureFormatter {

    fun format(measurement: Float): String

    fun toFloat(measurement: String): Float
}

object DefaultMeasureFormatter : MeasureFormatter {

    override fun format(measurement: Float): String = String.format(Locale.US, "%.1f", measurement)

    override fun toFloat(measurement: String): Float =
        if (measurement.isEmpty()) 0F else NumberFormat.getInstance(Locale.US).parse(measurement).toFloat()
}