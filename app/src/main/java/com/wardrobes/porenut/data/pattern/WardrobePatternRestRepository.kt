package com.wardrobes.porenut.data.pattern

import com.wardrobes.porenut.api.base.BaseProvider
import com.wardrobes.porenut.api.data.WardrobePatternService
import com.wardrobes.porenut.domain.WardrobePattern
import io.reactivex.Completable
import io.reactivex.Observable

object WardrobePatternRestRepository : WardrobePatternRepository {
    private val wardrobePatternService = BaseProvider.retrofit.create(WardrobePatternService::class.java)

    override fun getAll(): Observable<List<WardrobePattern>> = wardrobePatternService.getAll()

    override fun get(patternId: String): Observable<WardrobePattern> = wardrobePatternService.get(patternId)

    override fun add(wardrobePattern: WardrobePattern): Completable = wardrobePatternService.add(wardrobePattern)

    override fun update(wardrobePattern: WardrobePattern): Completable = wardrobePatternService.update(wardrobePattern.id, wardrobePattern)

    override fun delete(patternId: String): Completable = wardrobePatternService.delete(patternId)

}