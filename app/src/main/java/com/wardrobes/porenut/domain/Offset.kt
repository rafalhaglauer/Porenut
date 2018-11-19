package com.wardrobes.porenut.domain

data class Offset(
    val value: Float,
    val percentageValue: Float,
    val direction: Direction
) {
    enum class Direction {
        FORWARD, BACKWARD
    }
}