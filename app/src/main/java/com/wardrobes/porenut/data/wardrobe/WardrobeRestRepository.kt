package com.wardrobes.porenut.data.wardrobe

import com.wardrobes.porenut.api.base.BaseProvider
import com.wardrobes.porenut.api.data.WardrobeService
import com.wardrobes.porenut.domain.CreationType
import com.wardrobes.porenut.domain.Wardrobe
import io.reactivex.Completable
import io.reactivex.Observable

object WardrobeRestRepository : WardrobeRepository {

    private val wardrobeService = BaseProvider.retrofit.create(WardrobeService::class.java)

    override fun getAll(): Observable<List<Wardrobe>> = wardrobeService.getAll()

    override fun get(wardrobeId: String): Observable<Wardrobe> = wardrobeService.get(wardrobeId)

    override fun add(wardrobe: Wardrobe, creationType: CreationType): Completable = wardrobeService.add(wardrobe, creationType)

    override fun delete(wardrobeId: String): Completable = wardrobeService.delete(wardrobeId)

    override fun update(wardrobeId: String, wardrobe: Wardrobe): Completable = wardrobeService.update(wardrobeId, wardrobe)

}