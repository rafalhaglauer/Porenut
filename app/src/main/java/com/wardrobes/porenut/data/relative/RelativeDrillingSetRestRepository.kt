package com.wardrobes.porenut.data.relative

import com.wardrobes.porenut.api.base.BaseProvider
import com.wardrobes.porenut.api.data.RelativeDrillingSetInterface
import com.wardrobes.porenut.domain.RelativeDrillingSet
import io.reactivex.Completable
import io.reactivex.Observable

object RelativeDrillingSetRestRepository : RelativeDrillingSetRepository {
    private val compositionInterface = BaseProvider.retrofit.create(RelativeDrillingSetInterface::class.java)

    override fun getAll(): Observable<List<RelativeDrillingSet>> =
        compositionInterface.getAll()

    override fun get(compositionId: Long): Observable<RelativeDrillingSet> =
        compositionInterface.get(compositionId)

    override fun add(relativeDrillingSet: RelativeDrillingSet): Observable<Long> =
        compositionInterface.add(relativeDrillingSet)

    override fun delete(id: Long): Completable =
        compositionInterface.delete(id)

    override fun update(id: Long, relativeDrillingSet: RelativeDrillingSet): Completable =
        compositionInterface.update(id, relativeDrillingSet)
}