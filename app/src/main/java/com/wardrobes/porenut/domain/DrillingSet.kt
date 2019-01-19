package com.wardrobes.porenut.domain

import com.google.gson.annotations.SerializedName

data class ElementDrillingSetComposition(
    val id: Long,
    val drillingSet: RelativeDrillingSet,
    val element: Element,
    @SerializedName("xoffset") val xOffset: Offset,
    @SerializedName("yoffset") val yOffset: Offset
)

data class RelativeDrillingSet(val id: Long? = null, val name: String)