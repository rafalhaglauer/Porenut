package com.wardrobes.porenut.domain

data class Offset(
    val value: Float = 0F,
    val percentageValue: Float = 0F,
    val direction: Direction = Direction.FORWARD
) {
    enum class Direction {
        FORWARD, BACKWARD
    }
}