package com.wardrobes.porenut.data.relative

import com.wardrobes.porenut.domain.RelativeDrillingComposition
import com.wardrobes.porenut.domain.RelativeDrillingCompositionLight
import io.reactivex.Completable
import io.reactivex.Observable

interface RelativeDrillingCompositionRepository {

    fun getAll(): Observable<List<RelativeDrillingComposition>>

    fun get(compositionId: Long): Observable<RelativeDrillingComposition>

    fun add(relativeDrillingComposition: RelativeDrillingCompositionLight): Observable<Long>

    fun delete(id: Long): Completable

    fun update(id: Long, relativeDrillingComposition: RelativeDrillingCompositionLight): Completable
}