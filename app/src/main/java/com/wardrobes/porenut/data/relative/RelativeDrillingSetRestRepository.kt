package com.wardrobes.porenut.data.relative

import com.wardrobes.porenut.api.base.BaseProvider
import com.wardrobes.porenut.api.data.RelativeDrillingSetInterface
import com.wardrobes.porenut.domain.RelativeDrillingSet
import io.reactivex.Completable
import io.reactivex.Observable

object RelativeDrillingSetRestRepository : RelativeDrillingSetRepository {
    private val drillingSetInterface = BaseProvider.retrofit.create(RelativeDrillingSetInterface::class.java)

    override fun getAll(): Observable<List<RelativeDrillingSet>> =
        drillingSetInterface.getAll()

    override fun get(compositionId: Long): Observable<RelativeDrillingSet> =
        drillingSetInterface.get(compositionId)

    override fun add(relativeDrillingSet: RelativeDrillingSet): Observable<Long> =
        drillingSetInterface.add(relativeDrillingSet)

    override fun delete(id: Long): Completable =
        drillingSetInterface.delete(id)

    override fun update(id: Long, relativeDrillingSet: RelativeDrillingSet): Completable =
        drillingSetInterface.update(id, relativeDrillingSet)
}