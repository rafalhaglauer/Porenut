package com.wardrobes.porenut.data.drilling.set

import com.wardrobes.porenut.domain.RelativeDrillingSet
import io.reactivex.Completable
import io.reactivex.Observable

interface RelativeDrillingSetRepository {

    fun getAll(): Observable<List<RelativeDrillingSet>>

    fun get(compositionId: Long): Observable<RelativeDrillingSet>

    fun add(relativeDrillingSet: RelativeDrillingSet): Completable

    fun delete(id: Long): Completable

    fun update(id: Long, relativeDrillingSet: RelativeDrillingSet): Completable
}