package com.wardrobes.porenut.model

data class Wardrobe(val symbol: String, val width: Float, val height: Float, val depth: Float,
                    val type: WardrobeType)

enum class WardrobeType { HANGING, STANDING }