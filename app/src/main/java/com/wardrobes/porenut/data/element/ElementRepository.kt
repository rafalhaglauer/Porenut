package com.wardrobes.porenut.data.element

import com.wardrobes.porenut.domain.Element
import com.wardrobes.porenut.domain.ElementLight
import io.reactivex.Completable
import io.reactivex.Observable

interface ElementRepository {

    fun get(elementId: Long): Observable<Element>

    fun getAll(wardrobeId: Long): Observable<List<Element>>

    fun add(element: ElementLight): Observable<Long>

    fun update(elementId: Long, element: ElementLight): Observable<Unit>

    fun delete(elementId: Long): Completable

//    fun copy(elementId: Long, name: String): Observable<Long>
}