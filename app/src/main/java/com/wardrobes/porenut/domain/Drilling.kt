package com.wardrobes.porenut.domain

import com.google.gson.annotations.SerializedName

data class Drilling(
    @SerializedName("xposition") val xPosition: Float,
    @SerializedName("yposition") val yPosition: Float,
    val diameter: Float,
    val depth: Float
)

data class RelativeDrilling(
    val id: Long? = null,
    val name: String,
    @SerializedName("xoffset") val xOffset: Offset,
    @SerializedName("yoffset") val yOffset: Offset,
    val diameter: Float,
    val depth: Float
)