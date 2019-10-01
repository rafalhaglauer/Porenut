package com.wardrobes.porenut.data.element

import com.wardrobes.porenut.domain.Element
import io.reactivex.Completable
import io.reactivex.Observable

interface ElementRepository {

    fun get(elementId: Long): Observable<Element>

    fun getAll(wardrobeId: String): Observable<List<Element>>

    fun add(wardrobeId: String, element: Element): Completable

    fun update(elementId: Long, element: Element): Completable

    fun delete(elementId: Long): Completable
}