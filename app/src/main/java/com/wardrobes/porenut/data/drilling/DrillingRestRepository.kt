package com.wardrobes.porenut.data.drilling

import com.wardrobes.porenut.api.base.BaseProvider
import com.wardrobes.porenut.api.data.DrillingInterface
import com.wardrobes.porenut.domain.Drilling
import io.reactivex.Observable

object DrillingRestRepository : DrillingRepository {
    private val drillingInterface = BaseProvider.retrofit.create(DrillingInterface::class.java)

    override fun getAll(elementId: Long): Observable<List<Drilling>> = drillingInterface.getAll(elementId)

    override fun get(elementId: Long): Observable<Drilling> = drillingInterface.get(elementId)

    override fun create(elementId: Long, drilling: Drilling): Observable<Long> = drillingInterface.create(elementId, drilling)

    override fun delete(drillingId: Long): Observable<Any> = drillingInterface.delete(drillingId)

    override fun update(drillingId: Long, drilling: Drilling): Observable<Any> = drillingInterface.update(drillingId, drilling)
}