package com.wardrobes.porenut.data.wardrobe

import com.wardrobes.porenut.domain.CreationType
import com.wardrobes.porenut.domain.Wardrobe
import io.reactivex.Completable
import io.reactivex.Observable

interface WardrobeRepository {

    fun getAll(wardrobeType: Wardrobe.Type): Observable<List<Wardrobe>>

    fun get(wardrobeId: Long): Observable<Wardrobe>

    fun add(wardrobe: Wardrobe, creationType: CreationType): Completable

    fun delete(wardrobeId: Long): Completable

    fun update(wardrobeId: Long, wardrobe: Wardrobe): Completable

}