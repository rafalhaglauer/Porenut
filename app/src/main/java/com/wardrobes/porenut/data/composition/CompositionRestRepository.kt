package com.wardrobes.porenut.data.composition

import com.wardrobes.porenut.api.base.BaseProvider
import com.wardrobes.porenut.api.data.CompositionInterface
import com.wardrobes.porenut.domain.ReferenceElementRelativeDrillingComposition
import com.wardrobes.porenut.domain.ReferenceElementRelativeDrillingCompositionLight
import io.reactivex.Observable

object CompositionRestRepository : CompositionRepository {
    private val compositionInterface =
        BaseProvider.retrofit.create(CompositionInterface::class.java)

    override fun getAll(elementId: Long): Observable<List<ReferenceElementRelativeDrillingComposition>> =
        compositionInterface.getAll(elementId)

    override fun add(composition: ReferenceElementRelativeDrillingCompositionLight): Observable<Unit> =
        compositionInterface.add(composition)
}