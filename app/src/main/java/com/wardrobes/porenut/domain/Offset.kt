package com.wardrobes.porenut.domain

data class CompositeOffset(
    val offset: Offset,
    val percentageOffset: Offset
)

data class Offset(
    val value: Float,
    val reference: Reference
) {
    enum class Reference {
        BEGIN, END
    }
}