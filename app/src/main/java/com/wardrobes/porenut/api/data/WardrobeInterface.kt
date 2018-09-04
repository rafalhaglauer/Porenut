package com.wardrobes.porenut.api.data

import com.wardrobes.porenut.domain.Wardrobe
import com.wardrobes.porenut.domain.WardrobeLight
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.*

interface WardrobeInterface {

    @POST("/wardrobe/copy")
    fun copy(@Query("id") wardrobeId: Long, @Query("symbol") symbol: String): Observable<Long>

    @GET("/wardrobe")
    fun getAll(@Query("creationType") wardrobeCreationType: Wardrobe.CreationType): Observable<List<Wardrobe>>

    @GET("/wardrobe/{id}")
    fun get(@Path("id") wardrobeId: Long): Observable<Wardrobe>

    @POST("/wardrobe")
    fun add(@Body wardrobe: WardrobeLight): Observable<Long>

    @DELETE("/wardrobe/{id}")
    fun delete(@Path("id") wardrobeId: Long): Completable

    @PUT("/wardrobe/{id}")
    fun update(@Path("id") wardrobeId: Long, @Body wardrobe: WardrobeLight): Completable
}