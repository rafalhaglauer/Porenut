package com.wardrobes.porenut.data.relative

import com.wardrobes.porenut.domain.RelativeDrilling
import io.reactivex.Completable
import io.reactivex.Observable

interface RelativeDrillingRepository {

    fun getAll(compositionId: Long): Observable<List<RelativeDrilling>>

    fun get(drillingId: Long): Observable<RelativeDrilling>

    fun add(drillingSetId: Long, relativeDrilling: RelativeDrilling): Completable

    fun update(id: Long, relativeDrilling: RelativeDrilling): Completable

    fun delete(id: Long): Completable
}