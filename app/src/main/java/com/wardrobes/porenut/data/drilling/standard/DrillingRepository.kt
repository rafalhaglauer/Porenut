package com.wardrobes.porenut.data.drilling.standard

import com.wardrobes.porenut.domain.Drilling
import io.reactivex.Observable

interface DrillingRepository {

    fun getAll(elementId: Long): Observable<List<Drilling>>

}