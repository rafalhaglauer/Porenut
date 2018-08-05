package com.wardrobes.porenut.data.wardrobe

import com.wardrobes.porenut.api.base.BaseProvider
import com.wardrobes.porenut.api.data.WardrobeInterface
import com.wardrobes.porenut.domain.Wardrobe
import io.reactivex.Observable

object WardrobeRestRepository : WardrobeRepository {
    private val wardrobeInterface = BaseProvider.retrofit.create(WardrobeInterface::class.java)

    override fun getAll(wardrobeCreationType: Wardrobe.CreationType): Observable<List<Wardrobe>> = wardrobeInterface.getAll(wardrobeCreationType)

    override fun get(wardrobeId: Long): Observable<Wardrobe> = wardrobeInterface.get(wardrobeId)

    override fun add(wardrobe: Wardrobe, wardrobeCreationType: Wardrobe.CreationType): Observable<Long> = wardrobeInterface.add(wardrobe.createRequest(), wardrobeCreationType)

    override fun delete(wardrobeId: Long): Observable<Any> = wardrobeInterface.remove(wardrobeId)

    override fun update(wardrobe: Wardrobe): Observable<Long> = wardrobeInterface.update(wardrobe.id, wardrobe.createRequest())

    override fun copy(wardrobeId: Long, symbol: String): Observable<Long> = wardrobeInterface.copy(wardrobeId, symbol)
}