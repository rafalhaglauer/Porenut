package com.wardrobes.porenut.data.relative

import com.wardrobes.porenut.domain.RelativeDrilling
import com.wardrobes.porenut.domain.RelativeDrillingLight
import io.reactivex.Completable
import io.reactivex.Observable

interface RelativeDrillingRepository {

    fun getAll(compositionId: Long): Observable<List<RelativeDrilling>>

    fun get(drillingId: Long): Observable<RelativeDrilling>

    fun add(relativeDrilling: RelativeDrillingLight): Observable<Long>

    fun update(id: Long, relativeDrilling: RelativeDrillingLight): Completable

    fun delete(id: Long): Completable
}