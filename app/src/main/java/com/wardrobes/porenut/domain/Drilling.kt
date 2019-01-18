package com.wardrobes.porenut.domain

import com.google.gson.annotations.SerializedName

data class DrillingLight(
    val xPosition: Float,
    val yPosition: Float,
    val diameter: Float,
    val depth: Float,
    val elementId: Long
)

data class Drilling(
    @SerializedName("xposition") val xPosition: Float,
    @SerializedName("yposition") val yPosition: Float,
    val diameter: Float,
    val depth: Float
)

data class RelativeDrillingLight(
    val name: String,
    val xOffset: Offset,
    val yOffset: Offset,
    val diameter: Float,
    val depth: Float,
    val relativeDrillingCompositionId: Long
)

data class RelativeDrilling(
    val id: Long,
    val name: String,
    @SerializedName("xoffset") val xOffset: Offset,
    @SerializedName("yoffset") val yOffset: Offset,
    val diameter: Float,
    val depth: Float
)