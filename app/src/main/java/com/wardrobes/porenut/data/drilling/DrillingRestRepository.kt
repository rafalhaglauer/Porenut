package com.wardrobes.porenut.data.drilling

import com.wardrobes.porenut.api.base.BaseProvider
import com.wardrobes.porenut.api.data.DrillingInterface
import com.wardrobes.porenut.domain.Drilling
import io.reactivex.Observable

object DrillingRestRepository : DrillingRepository {
    private val drillingInterface = BaseProvider.retrofit.create(DrillingInterface::class.java)

    override fun get(elementId: Long): Observable<List<Drilling>> = drillingInterface.get(elementId)
}