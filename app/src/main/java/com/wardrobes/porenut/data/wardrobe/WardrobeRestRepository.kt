package com.wardrobes.porenut.data.wardrobe

import com.wardrobes.porenut.api.base.BaseProvider
import com.wardrobes.porenut.api.data.WardrobeInterface
import com.wardrobes.porenut.domain.CreationType
import com.wardrobes.porenut.domain.Wardrobe
import io.reactivex.Completable
import io.reactivex.Observable

object WardrobeRestRepository : WardrobeRepository {
    private val wardrobeInterface = BaseProvider.retrofit.create(WardrobeInterface::class.java)

    override fun getAll(wardrobeType: Wardrobe.Type): Observable<List<Wardrobe>> =
        wardrobeInterface.getAll(wardrobeType)

    override fun get(wardrobeId: Long): Observable<Wardrobe> = wardrobeInterface.get(wardrobeId)

    override fun add(wardrobe: Wardrobe, creationType: CreationType): Observable<Long> =
        wardrobeInterface.add(wardrobe, creationType)

    override fun delete(wardrobeId: Long): Completable = wardrobeInterface.delete(wardrobeId)

    override fun update(wardrobeId: Long, wardrobe: Wardrobe): Completable =
        wardrobeInterface.update(wardrobeId, wardrobe)

//    override fun copy(wardrobeId: Long, symbol: String): Observable<Long> = wardrobeInterface.copy(wardrobeId, symbol)
}