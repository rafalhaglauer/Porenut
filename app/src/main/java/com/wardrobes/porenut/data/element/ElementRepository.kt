package com.wardrobes.porenut.data.element

import com.wardrobes.porenut.domain.Element
import io.reactivex.Completable
import io.reactivex.Observable

interface ElementRepository {

    fun get(elementId: Long): Observable<Element>

    fun getAll(wardrobeId: Long): Observable<List<Element>>

    fun add(element: Element): Observable<Long>

    fun update(elementId: Long, element: Element): Observable<Unit>

    fun delete(elementId: Long): Completable
}