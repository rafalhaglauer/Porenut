package com.wardrobes.porenut.data.drilling.relative

import com.wardrobes.porenut.api.base.BaseProvider
import com.wardrobes.porenut.api.data.RelativeDrillingService
import com.wardrobes.porenut.domain.RelativeDrilling
import io.reactivex.Completable
import io.reactivex.Observable

object RelativeDrillingRestRepository : RelativeDrillingRepository {
    private val drillingService = BaseProvider.retrofit.create(RelativeDrillingService::class.java)

    override fun getAll(compositionId: Long): Observable<List<RelativeDrilling>> =
        drillingService.getAll(compositionId)

    override fun get(drillingId: Long): Observable<RelativeDrilling> =
        drillingService.get(drillingId)

    override fun delete(id: Long): Completable = drillingService.delete(id)

    override fun add(drillingSetId: Long, relativeDrilling: RelativeDrilling): Completable =
        drillingService.add(drillingSetId, relativeDrilling)

    override fun update(id: Long, relativeDrilling: RelativeDrilling): Completable =
        drillingService.update(id, relativeDrilling)
}