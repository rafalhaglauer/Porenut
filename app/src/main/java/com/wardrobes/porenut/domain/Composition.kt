package com.wardrobes.porenut.domain

data class ReferenceElementRelativeDrillingCompositionLight(
    val relativeDrillingCompositionId: Long,
    val elementId: Long,
    val xOffset: Offset,
    val yOffset: Offset
)

data class ReferenceElementRelativeDrillingComposition(
    val id: Long,
    val drillingSet: RelativeDrillingSet,
    val element: Element,
    val xOffset: Offset,
    val yOffset: Offset
)

data class RelativeDrillingCompositionLight(
    val name: String,
    val suggestXReferenceValue: String,
    val suggestYReferenceValue: String
)

data class RelativeDrillingSet(val id: Long, val name: String)