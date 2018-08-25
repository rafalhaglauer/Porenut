package com.wardrobes.porenut.data.relative

import com.wardrobes.porenut.api.base.BaseProvider
import com.wardrobes.porenut.api.data.RelativeDrillingInterface
import com.wardrobes.porenut.domain.RelativeDrilling
import com.wardrobes.porenut.domain.RelativeDrillingLight
import io.reactivex.Observable

object RelativeDrillingRestRepository : RelativeDrillingRepository {
    private val drillingInterface =
        BaseProvider.retrofit.create(RelativeDrillingInterface::class.java)

    override fun getAll(compositionId: Long): Observable<List<RelativeDrilling>> =
        drillingInterface.getAll(compositionId)

    override fun get(drillingId: Long): Observable<RelativeDrilling> =
        drillingInterface.get(drillingId)

    override fun add(relativeDrilling: RelativeDrillingLight): Observable<Long> =
        drillingInterface.add(relativeDrilling)

    override fun update(id: Long, relativeDrilling: RelativeDrillingLight): Observable<Unit> =
        drillingInterface.update(id, relativeDrilling)
}