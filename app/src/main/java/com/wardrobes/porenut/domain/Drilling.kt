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
    val id: Long,
    @SerializedName("xposition") val xPosition: Float,
    @SerializedName("yposition") val yPosition: Float,
    val diameter: Float,
    val depth: Float,
    val type: CreationType
) {
    enum class CreationType {
        CUSTOM, GENERATED
    }
}

data class RelativeDrillingLight(
    val name: String,
    val xOffset: CompositeOffset,
    val yOffset: CompositeOffset,
    val diameter: Float,
    val depth: Float,
    val relativeDrillingCompositionId: Long
)

data class RelativeDrilling(
    val id: Long,
    val name: String,
    val xOffset: CompositeOffset,
    val yOffset: CompositeOffset,
    val diameter: Float,
    val depth: Float,
    val relativeDrillingCompositionId: Long
)