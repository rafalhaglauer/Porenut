package com.wardrobes.porenut.domain

data class ReferenceElementRelativeDrillingCompositionLight(
    val relativeDrillingCompositionId: Long,
    val referenceElementId: Long,
    val elementId: Long,
    val xOffset: Offset,
    val yOffset: Offset,
    val xReferenceLength: Element.LengthType,
    val yReferenceLength: Element.LengthType
)

data class ReferenceElementRelativeDrillingComposition(
    val id: Long,
    val relativeDrillingComposition: RelativeDrillingComposition,
    val referenceElement: Element,
    val element: Element,
    val xOffset: Offset,
    val yOffset: Offset,
    val xReferenceLength: Element.LengthType,
    val yReferenceLength: Element.LengthType
)

data class RelativeDrillingCompositionLight(
    val name: String,
    val suggestXReferenceValue: String,
    val suggestYReferenceValue: String
)

data class RelativeDrillingComposition(
    val id: Long,
    val name: String,
    val suggestXReferenceValue: String,
    val suggestYReferenceValue: String
)