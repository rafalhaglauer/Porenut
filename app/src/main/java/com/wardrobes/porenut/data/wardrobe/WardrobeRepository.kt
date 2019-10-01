package com.wardrobes.porenut.data.wardrobe

import com.wardrobes.porenut.domain.CreationType
import com.wardrobes.porenut.domain.Wardrobe
import io.reactivex.Completable
import io.reactivex.Observable

interface WardrobeRepository {

    fun getAll(): Observable<List<Wardrobe>>

    fun get(wardrobeId: String): Observable<Wardrobe>

    fun add(wardrobe: Wardrobe, creationType: CreationType): Completable

    fun delete(wardrobeId: String): Completable

    fun update(wardrobeId: String, wardrobe: Wardrobe): Completable

}