package com.wardrobes.porenut.data.composition

import com.wardrobes.porenut.domain.ElementDrillingSetComposition
import io.reactivex.Completable
import io.reactivex.Observable

interface CompositionRepository {

    fun getAll(elementId: Long): Observable<List<ElementDrillingSetComposition>>

    fun get(compositionId: Long): Observable<ElementDrillingSetComposition>

    fun delete(compositionId: Long): Completable

    fun update(compositionId: Long, composition: ElementDrillingSetCompositionRequest): Completable

    fun add(composition: ElementDrillingSetCompositionRequest): Completable
}