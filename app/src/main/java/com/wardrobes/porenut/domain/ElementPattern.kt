package com.wardrobes.porenut.domain

data class ElementPattern(
    val id: String = "",
    val name: String,
    val length: LengthPattern,
    val width: LengthPattern,
    val height: Int
)

data class LengthPattern(
    val id: String = "",
    val pattern: String
)