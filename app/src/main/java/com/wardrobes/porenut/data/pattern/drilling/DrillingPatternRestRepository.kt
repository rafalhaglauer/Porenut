package com.wardrobes.porenut.data.pattern.drilling

import com.wardrobes.porenut.api.base.BaseProvider
import com.wardrobes.porenut.api.data.DrillingPatternService
import com.wardrobes.porenut.domain.DrillingPattern
import io.reactivex.Completable
import io.reactivex.Observable

object DrillingPatternRestRepository : DrillingPatternRepository {

    private val drillingPatternService = BaseProvider.retrofit.create(DrillingPatternService::class.java)

    override fun getAll(wardrobePatternId: String): Observable<List<DrillingPattern>> = drillingPatternService.getAll(wardrobePatternId)

    override fun get(patternId: String): Observable<DrillingPattern> = drillingPatternService.get(patternId)

    override fun add(wardrobePatternId: String, drillingPattern: DrillingPattern): Completable = drillingPatternService.add(wardrobePatternId, drillingPattern)

    override fun update(drillingPattern: DrillingPattern): Completable = drillingPatternService.update(drillingPattern.id, drillingPattern)

    override fun delete(patternId: String): Completable = drillingPatternService.delete(patternId)

}