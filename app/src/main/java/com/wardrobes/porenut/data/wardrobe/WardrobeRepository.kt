package com.wardrobes.porenut.data.wardrobe

import com.wardrobes.porenut.domain.Wardrobe
import io.reactivex.Observable

interface WardrobeRepository {

    fun getAll(wardrobeCreationType: Wardrobe.CreationType): Observable<List<Wardrobe>>

    fun get(wardrobeId: Long): Observable<Wardrobe>

    fun add(wardrobe: Wardrobe, wardrobeCreationType: Wardrobe.CreationType): Observable<Long>

    fun delete(wardrobeId: Long): Observable<Any>

    fun update(wardrobe: Wardrobe): Observable<Long>

    fun copy(wardrobeId: Long, symbol: String): Observable<Long>
}