package com.wardrobes.porenut.data.pattern.element

import com.wardrobes.porenut.api.base.BaseProvider
import com.wardrobes.porenut.api.data.ElementPatternService
import com.wardrobes.porenut.domain.ElementPattern
import io.reactivex.Completable
import io.reactivex.Observable

object ElementPatternRestRepository : ElementPatternRepository {

    private val elementPatternService = BaseProvider.retrofit.create(ElementPatternService::class.java)

    override fun getAll(wardrobePatternId: String): Observable<List<ElementPattern>> = elementPatternService.getAll(wardrobePatternId)

    override fun get(patternId: String): Observable<ElementPattern> = elementPatternService.get(patternId)

    override fun add(wardrobePatternId: String, elementPattern: ElementPattern): Completable = elementPatternService.add(wardrobePatternId, elementPattern)

    override fun update(elementPattern: ElementPattern): Completable = elementPatternService.update(elementPattern.id, elementPattern)

    override fun delete(patternId: String): Completable = elementPatternService.delete(patternId)

}