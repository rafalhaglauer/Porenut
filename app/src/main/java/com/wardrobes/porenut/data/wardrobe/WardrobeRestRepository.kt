package com.wardrobes.porenut.data.wardrobe

import com.wardrobes.porenut.api.base.BaseProvider
import com.wardrobes.porenut.api.data.WardrobeService
import com.wardrobes.porenut.domain.CreationType
import com.wardrobes.porenut.domain.Wardrobe
import io.reactivex.Completable
import io.reactivex.Observable

object WardrobeRestRepository : WardrobeRepository {
    private val wardrobeService = BaseProvider.retrofit.create(WardrobeService::class.java)

    override fun getAll(wardrobeType: Wardrobe.Type): Observable<List<Wardrobe>> =
        wardrobeService.getAll(wardrobeType)

    override fun get(wardrobeId: Long): Observable<Wardrobe> = wardrobeService.get(wardrobeId)

    override fun add(wardrobe: Wardrobe, creationType: CreationType): Completable =
        wardrobeService.add(wardrobe, creationType)

    override fun delete(wardrobeId: Long): Completable = wardrobeService.delete(wardrobeId)

    override fun update(wardrobeId: Long, wardrobe: Wardrobe): Completable =
        wardrobeService.update(wardrobeId, wardrobe)

}