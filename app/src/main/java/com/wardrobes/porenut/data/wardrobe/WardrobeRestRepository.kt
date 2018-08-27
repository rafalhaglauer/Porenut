package com.wardrobes.porenut.data.wardrobe

import com.wardrobes.porenut.api.base.BaseProvider
import com.wardrobes.porenut.api.data.WardrobeInterface
import com.wardrobes.porenut.domain.Wardrobe
import com.wardrobes.porenut.domain.WardrobeLight
import io.reactivex.Observable

object WardrobeRestRepository : WardrobeRepository {
    private val wardrobeInterface = BaseProvider.retrofit.create(WardrobeInterface::class.java)

    override fun getAll(wardrobeCreationType: Wardrobe.CreationType): Observable<List<Wardrobe>> =
        wardrobeInterface.getAll(wardrobeCreationType)

    override fun get(wardrobeId: Long): Observable<Wardrobe> = wardrobeInterface.get(wardrobeId)

    override fun add(wardrobe: WardrobeLight): Observable<Long> = wardrobeInterface.add(wardrobe)

    override fun delete(wardrobeId: Long): Observable<Unit> = wardrobeInterface.delete(wardrobeId)

    override fun update(wardrobeId: Long, wardrobe: WardrobeLight): Observable<Unit> =
        wardrobeInterface.update(wardrobeId, wardrobe)

//    override fun copy(wardrobeId: Long, symbol: String): Observable<Long> = wardrobeInterface.copy(wardrobeId, symbol)
}