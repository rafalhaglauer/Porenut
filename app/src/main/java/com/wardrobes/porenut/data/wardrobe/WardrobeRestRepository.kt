package com.wardrobes.porenut.data.wardrobe

import com.wardrobes.porenut.api.base.BaseProvider
import com.wardrobes.porenut.api.data.WardrobeInterface
import com.wardrobes.porenut.domain.Wardrobe
import io.reactivex.Observable

object WardrobeRestRepository : WardrobeRepository {
    private val wardrobeInterface = BaseProvider.retrofit.create(WardrobeInterface::class.java)

    override fun getAll(): Observable<List<Wardrobe>> = wardrobeInterface.getAll()

    override fun get(wardrobeId: Long): Observable<Wardrobe> = wardrobeInterface.get(wardrobeId)

    override fun add(wardrobe: Wardrobe): Observable<Any> = wardrobeInterface.add(wardrobe.createRequest())
}