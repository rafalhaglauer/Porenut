package com.wardrobes.porenut.data.element

import com.wardrobes.porenut.api.base.BaseProvider
import com.wardrobes.porenut.api.data.ElementService
import com.wardrobes.porenut.domain.Element
import io.reactivex.Completable
import io.reactivex.Observable

object ElementRestRepository : ElementRepository {
    private val elementService = BaseProvider.retrofit.create(ElementService::class.java)

    override fun get(elementId: Long): Observable<Element> = elementService.get(elementId)

    override fun getAll(wardrobeId: Long): Observable<List<Element>> = elementService.getAll(wardrobeId)

    override fun add(wardrobeId: Long, element: Element): Completable = elementService.add(element, wardrobeId)

    override fun update(elementId: Long, element: Element): Completable = elementService.update(elementId, element)

    override fun delete(elementId: Long): Completable = elementService.delete(elementId)
}