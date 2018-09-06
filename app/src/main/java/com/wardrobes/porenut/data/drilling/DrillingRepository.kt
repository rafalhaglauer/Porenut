package com.wardrobes.porenut.data.drilling

import com.wardrobes.porenut.domain.Drilling
import com.wardrobes.porenut.domain.DrillingLight
import io.reactivex.Completable
import io.reactivex.Observable

interface DrillingRepository {

    fun getAll(elementId: Long): Observable<List<Drilling>>

    fun get(drillingId: Long): Observable<Drilling>

    fun add(drilling: DrillingLight): Observable<Long>

    fun delete(drillingId: Long): Completable

    fun update(drillingId: Long, drilling: DrillingLight): Completable
}