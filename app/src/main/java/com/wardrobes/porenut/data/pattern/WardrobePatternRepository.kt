package com.wardrobes.porenut.data.pattern

import com.wardrobes.porenut.domain.WardrobePattern
import io.reactivex.Completable
import io.reactivex.Observable

interface WardrobePatternRepository {

    fun getAll(): Observable<List<WardrobePattern>>

    fun get(patternId: String): Observable<WardrobePattern>

    fun add(wardrobePattern: WardrobePattern): Completable

    fun update(wardrobePattern: WardrobePattern): Completable

}