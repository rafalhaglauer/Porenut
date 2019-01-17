package com.wardrobes.porenut.data.element

import com.wardrobes.porenut.api.base.BaseProvider
import com.wardrobes.porenut.api.data.ElementInterface
import com.wardrobes.porenut.domain.Element
import io.reactivex.Completable
import io.reactivex.Observable

object ElementRestRepository : ElementRepository {
    private val elementInterface = BaseProvider.retrofit.create(ElementInterface::class.java)

    override fun get(elementId: Long): Observable<Element> = elementInterface.get(elementId)

    override fun getAll(wardrobeId: Long): Observable<List<Element>> = elementInterface.getAll(wardrobeId)

    override fun add(element: Element): Observable<Long> = elementInterface.add(element, element.wardrobeId)

    override fun update(elementId: Long, element: Element): Observable<Unit> = elementInterface.update(elementId, element)

    override fun delete(elementId: Long): Completable = elementInterface.delete(elementId)
}