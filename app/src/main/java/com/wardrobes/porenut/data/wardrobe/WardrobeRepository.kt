package com.wardrobes.porenut.data.wardrobe

import com.wardrobes.porenut.domain.Wardrobe
import io.reactivex.Observable

interface WardrobeRepository {

    fun getAll(): Observable<List<Wardrobe>>

    fun get(wardrobeId: Long): Observable<Wardrobe>

    fun add(wardrobe: Wardrobe): Observable<Any>
}