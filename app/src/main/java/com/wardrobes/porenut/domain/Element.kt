package com.wardrobes.porenut.domain

data class Element(
    val id: Long? = null,
    val name: String,
    val width: Float,
    val length: Float,
    val height: Float
)