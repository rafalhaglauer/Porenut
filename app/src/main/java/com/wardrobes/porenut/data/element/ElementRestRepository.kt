package com.wardrobes.porenut.data.element

import com.wardrobes.porenut.api.base.BaseProvider
import com.wardrobes.porenut.api.data.ElementInterface
import com.wardrobes.porenut.domain.Element
import com.wardrobes.porenut.domain.ElementLight
import io.reactivex.Completable
import io.reactivex.Observable

object ElementRestRepository : ElementRepository {
    private val elementInterface = BaseProvider.retrofit.create(ElementInterface::class.java)

    override fun get(elementId: Long): Observable<Element> = elementInterface.get(elementId)

    override fun getAll(wardrobeId: Long): Observable<List<Element>> = elementInterface.getAll(wardrobeId)

    override fun add(element: ElementLight): Observable<Long> = elementInterface.add(element)

    override fun update(elementId: Long, element: ElementLight): Observable<Unit> = elementInterface.update(elementId, element)

    override fun delete(elementId: Long): Completable = elementInterface.delete(elementId)

//    override fun copy(elementId: Long, name: String): Observable<Long> = elementInterface.copy(elementId, name)
}