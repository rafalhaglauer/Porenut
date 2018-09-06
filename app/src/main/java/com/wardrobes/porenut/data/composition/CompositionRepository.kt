package com.wardrobes.porenut.data.composition

import com.wardrobes.porenut.domain.ReferenceElementRelativeDrillingComposition
import com.wardrobes.porenut.domain.ReferenceElementRelativeDrillingCompositionLight
import io.reactivex.Completable
import io.reactivex.Observable

interface CompositionRepository {

    fun getAll(elementId: Long): Observable<List<ReferenceElementRelativeDrillingComposition>>

    fun get(compositionId: Long): Observable<ReferenceElementRelativeDrillingComposition>

    fun delete(compositionId: Long): Completable

    fun update(compositionId: Long, composition: ReferenceElementRelativeDrillingCompositionLight): Completable

    fun add(composition: ReferenceElementRelativeDrillingCompositionLight): Completable
}