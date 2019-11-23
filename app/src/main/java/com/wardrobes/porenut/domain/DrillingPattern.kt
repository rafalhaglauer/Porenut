package com.wardrobes.porenut.domain

data class DrillingPattern(
    val id: String = "",
    val name: String,
    val positionX: PositionPattern,
    val positionY: PositionPattern,
    val destination: DrillingDestination
)

data class PositionPattern(
    val id: String = "",
    val pattern: String
)

enum class DrillingDestination {

    CONFIRMAT_SCREW, SUPPORT, DOWEL, UNKNOWN

}