package com.wardrobes.porenut.data.pattern.drilling

import com.wardrobes.porenut.domain.DrillingPattern
import io.reactivex.Completable
import io.reactivex.Observable

interface DrillingPatternRepository {

    fun getAll(wardrobePatternId: String): Observable<List<DrillingPattern>>

    fun get(patternId: String): Observable<DrillingPattern>

    fun add(wardrobePatternId: String, drillingPattern: DrillingPattern): Completable

    fun update(drillingPattern: DrillingPattern): Completable

    fun delete(patternId: String): Completable

}