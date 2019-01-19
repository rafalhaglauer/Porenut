package com.wardrobes.porenut.data.drilling.standard

import com.wardrobes.porenut.api.base.BaseProvider
import com.wardrobes.porenut.api.data.DrillingService
import com.wardrobes.porenut.domain.Drilling
import io.reactivex.Observable

object DrillingRestRepository : DrillingRepository {
    private val drillingService = BaseProvider.retrofit.create(DrillingService::class.java)

    override fun getAll(elementId: Long): Observable<List<Drilling>> = drillingService.getAll(elementId)
}