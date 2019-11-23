package com.wardrobes.porenut.data.pattern.element

import com.wardrobes.porenut.domain.ElementPattern
import io.reactivex.Completable
import io.reactivex.Observable

interface ElementPatternRepository {

    fun getAll(wardrobePatternId: String): Observable<List<ElementPattern>>

    fun get(patternId: String): Observable<ElementPattern>

    fun add(wardrobePatternId: String, elementPattern: ElementPattern): Completable

    fun update(elementPattern: ElementPattern): Completable

    fun delete(patternId: String): Completable

}