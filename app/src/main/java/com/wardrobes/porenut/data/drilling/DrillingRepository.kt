package com.wardrobes.porenut.data.drilling

import com.wardrobes.porenut.domain.Drilling
import io.reactivex.Observable

interface DrillingRepository {

    fun getAll(elementId: Long): Observable<List<Drilling>>

    fun get(drillingId: Long): Observable<Drilling>

    fun create(elementId: Long, drilling: Drilling): Observable<Long>

    fun delete(drillingId: Long): Observable<Any>

    fun update(drillingId: Long, toDrilling: Drilling): Observable<Any>
}