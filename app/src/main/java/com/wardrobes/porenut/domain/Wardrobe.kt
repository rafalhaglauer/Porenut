package com.wardrobes.porenut.domain

data class Wardrobe(
    val id: String = "",
    val symbol: String,
    val width: Float,
    val height: Float,
    val depth: Float,
    val type: Type
) {
    enum class Type { UPPER, BOTTOM }
}

enum class CreationType { CUSTOM, GENERATE }