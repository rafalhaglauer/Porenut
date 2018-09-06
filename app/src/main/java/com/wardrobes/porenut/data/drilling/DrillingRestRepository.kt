package com.wardrobes.porenut.data.drilling

import com.wardrobes.porenut.api.base.BaseProvider
import com.wardrobes.porenut.api.data.DrillingInterface
import com.wardrobes.porenut.domain.Drilling
import com.wardrobes.porenut.domain.DrillingLight
import io.reactivex.Completable
import io.reactivex.Observable

object DrillingRestRepository : DrillingRepository {
    private val drillingInterface = BaseProvider.retrofit.create(DrillingInterface::class.java)

    override fun getAll(elementId: Long): Observable<List<Drilling>> = drillingInterface.getAll(elementId)

    override fun get(drillingId: Long): Observable<Drilling> = drillingInterface.get(drillingId)

    override fun add(drilling: DrillingLight): Observable<Long> = drillingInterface.add(drilling)

    override fun delete(drillingId: Long): Completable = drillingInterface.delete(drillingId)

    override fun update(drillingId: Long, drilling: DrillingLight): Completable = drillingInterface.update(drillingId, drilling)
}