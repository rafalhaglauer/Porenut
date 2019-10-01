package com.wardrobes.porenut.data.composition

import com.wardrobes.porenut.api.base.BaseProvider
import com.wardrobes.porenut.api.data.ElementDrillingSetCompositionService
import com.wardrobes.porenut.domain.ElementDrillingSetComposition
import com.wardrobes.porenut.domain.Offset
import io.reactivex.Completable
import io.reactivex.Observable

object ElementDrillingSetCompositionRestRepository : ElementDrillingSetCompositionRepository {
    private val compositionService = BaseProvider.retrofit.create(ElementDrillingSetCompositionService::class.java)

    override fun getAll(elementId: Long): Observable<List<ElementDrillingSetComposition>> =
        compositionService.getAll(elementId)

    override fun get(compositionId: Long): Observable<ElementDrillingSetComposition> {
        return compositionService.get(compositionId)
    }

    override fun delete(compositionId: Long): Completable {
        return compositionService.delete(compositionId)
    }

    override fun update(compositionId: Long, composition: ElementDrillingSetCompositionRequest): Completable {
        return compositionService.update(compositionId, composition)
    }

    override fun add(composition: ElementDrillingSetCompositionRequest): Completable =
        compositionService.add(composition)
}

data class ElementDrillingSetCompositionRequest(
    val drillingSetId: Long,
    val elementId: Long,
    val xOffset: Offset,
    val yOffset: Offset
)