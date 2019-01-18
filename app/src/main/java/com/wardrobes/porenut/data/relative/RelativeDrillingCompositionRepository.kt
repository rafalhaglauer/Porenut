package com.wardrobes.porenut.data.relative

import com.wardrobes.porenut.domain.RelativeDrillingCompositionLight
import com.wardrobes.porenut.domain.RelativeDrillingSet
import io.reactivex.Completable
import io.reactivex.Observable

interface RelativeDrillingCompositionRepository {

    fun getAll(): Observable<List<RelativeDrillingSet>>

    fun get(compositionId: Long): Observable<RelativeDrillingSet>

    fun add(relativeDrillingComposition: RelativeDrillingCompositionLight): Observable<Long>

    fun delete(id: Long): Completable

    fun update(id: Long, relativeDrillingComposition: RelativeDrillingCompositionLight): Completable
}