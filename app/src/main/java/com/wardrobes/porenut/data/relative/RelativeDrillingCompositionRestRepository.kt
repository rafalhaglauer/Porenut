package com.wardrobes.porenut.data.relative

import com.wardrobes.porenut.api.base.BaseProvider
import com.wardrobes.porenut.api.data.RelativeDrillingCompositionInterface
import com.wardrobes.porenut.domain.RelativeDrillingCompositionLight
import com.wardrobes.porenut.domain.RelativeDrillingSet
import io.reactivex.Completable
import io.reactivex.Observable

object RelativeDrillingCompositionRestRepository : RelativeDrillingCompositionRepository {
    private val compositionInterface =
        BaseProvider.retrofit.create(RelativeDrillingCompositionInterface::class.java)

    override fun getAll(): Observable<List<RelativeDrillingSet>> =
        compositionInterface.getAll()

    override fun get(compositionId: Long): Observable<RelativeDrillingSet> =
        compositionInterface.get(compositionId)

    override fun add(relativeDrillingComposition: RelativeDrillingCompositionLight): Observable<Long> =
        compositionInterface.add(relativeDrillingComposition)

    override fun delete(id: Long): Completable =
        compositionInterface.delete(id)

    override fun update(
        id: Long,
        relativeDrillingComposition: RelativeDrillingCompositionLight
    ): Completable =
        compositionInterface.update(id, relativeDrillingComposition)
}