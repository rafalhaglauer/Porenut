package com.wardrobes.porenut.data.wardrobe

import com.wardrobes.porenut.domain.Wardrobe
import com.wardrobes.porenut.domain.WardrobeLight
import io.reactivex.Observable

interface WardrobeRepository {

    fun getAll(wardrobeCreationType: Wardrobe.CreationType): Observable<List<Wardrobe>>

    fun get(wardrobeId: Long): Observable<Wardrobe>

    fun add(wardrobe: WardrobeLight): Observable<Long>

    fun delete(wardrobeId: Long): Observable<Unit>

    fun update(wardrobeId: Long, wardrobe: WardrobeLight): Observable<Unit>

//    fun copy(wardrobeId: Long, symbol: String): Observable<Long>
}