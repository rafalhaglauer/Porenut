package com.wardrobes.porenut.domain

data class Wardrobe(
    val id: Long? = null,
    val symbol: String,
    val width: Float,
    val height: Float,
    val depth: Float,
    val type: Wardrobe.Type
) {
    enum class Type { UPPER, BOTTOM }
}

enum class CreationType { CUSTOM, GENERATE }