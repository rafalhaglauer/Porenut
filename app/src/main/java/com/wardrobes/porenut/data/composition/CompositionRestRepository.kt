package com.wardrobes.porenut.data.composition

import com.wardrobes.porenut.api.base.BaseProvider
import com.wardrobes.porenut.api.data.CompositionInterface
import com.wardrobes.porenut.domain.ElementDrillingSetComposition
import com.wardrobes.porenut.domain.Offset
import io.reactivex.Completable
import io.reactivex.Observable

object CompositionRestRepository : CompositionRepository {
    private val compositionInterface = BaseProvider.retrofit.create(CompositionInterface::class.java)

    override fun getAll(elementId: Long): Observable<List<ElementDrillingSetComposition>> =
        compositionInterface.getAll(elementId)

    override fun get(compositionId: Long): Observable<ElementDrillingSetComposition> {
        return compositionInterface.get(compositionId)
    }

    override fun delete(compositionId: Long): Completable {
        return compositionInterface.delete(compositionId)
    }

    override fun update(compositionId: Long, composition: ElementDrillingSetCompositionRequest): Completable {
        return compositionInterface.update(compositionId, composition)
    }

    override fun add(composition: ElementDrillingSetCompositionRequest): Completable =
        compositionInterface.add(composition)
}

data class ElementDrillingSetCompositionRequest(
    val drillingSetId: Long,
    val elementId: Long,
    val xOffset: Offset,
    val yOffset: Offset
)