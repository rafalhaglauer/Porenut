package com.wardrobes.porenut.domain

data class ElementLight(
    val name: String,
    val length: Float,
    val width: Float,
    val height: Float,
    val wardrobeId: Long
)

data class Element(
    val id: Long,
    val name: String,
    val width: Float,
    val length: Float,
    val height: Float,
    val creationType: CreationType
) {
    enum class LengthType {
        LENGTH, WIDTH, HEIGHT
    }

    enum class CreationType {
        CUSTOM, GENERATED
    }
}