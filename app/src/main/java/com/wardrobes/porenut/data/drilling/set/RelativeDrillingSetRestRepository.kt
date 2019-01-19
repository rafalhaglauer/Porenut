package com.wardrobes.porenut.data.drilling.set

import com.wardrobes.porenut.api.base.BaseProvider
import com.wardrobes.porenut.api.data.RelativeDrillingSetService
import com.wardrobes.porenut.domain.RelativeDrillingSet
import io.reactivex.Completable
import io.reactivex.Observable

object RelativeDrillingSetRestRepository : RelativeDrillingSetRepository {
    private val drillingSetService = BaseProvider.retrofit.create(RelativeDrillingSetService::class.java)

    override fun getAll(): Observable<List<RelativeDrillingSet>> =
        drillingSetService.getAll()

    override fun get(compositionId: Long): Observable<RelativeDrillingSet> =
        drillingSetService.get(compositionId)

    override fun add(relativeDrillingSet: RelativeDrillingSet): Completable =
        drillingSetService.add(relativeDrillingSet)

    override fun delete(id: Long): Completable =
        drillingSetService.delete(id)

    override fun update(id: Long, relativeDrillingSet: RelativeDrillingSet): Completable =
        drillingSetService.update(id, relativeDrillingSet)
}