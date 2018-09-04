package com.wardrobes.porenut.domain

const val UNDEFINED_ID: Long = -1

data class WardrobeLight(
    val symbol: String,
    val width: Float,
    val height: Float,
    val depth: Float,
    val type: Wardrobe.Type,
    val creationType: Wardrobe.CreationType
)

data class Wardrobe(
    val id: Long,
    val symbol: String,
    val width: Float,
    val height: Float,
    val depth: Float,
    val type: Wardrobe.Type,
    val creationType: Wardrobe.CreationType
) {
    enum class Type { HANGING, STANDING }

    enum class CreationType { CUSTOM, STANDARD }
}
