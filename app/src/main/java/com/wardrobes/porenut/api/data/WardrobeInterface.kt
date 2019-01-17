package com.wardrobes.porenut.api.data

import com.wardrobes.porenut.domain.CreationType
import com.wardrobes.porenut.domain.Wardrobe
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.*

interface WardrobeInterface {

    @POST("/wardrobe/copy")
    fun copy(@Query("id") wardrobeId: Long, @Query("symbol") symbol: String): Observable<Long>

    @GET("/wardrobe")
    fun getAll(@Query("wardrobeType") wardrobeType: Wardrobe.Type): Observable<List<Wardrobe>>

    @GET("/wardrobe/{id}")
    fun get(@Path("id") wardrobeId: Long): Observable<Wardrobe>

    @POST("/wardrobe")
    fun add(@Body wardrobe: Wardrobe, @Query("creationType") creationType: CreationType): Observable<Long>

    @DELETE("/wardrobe/{id}")
    fun delete(@Path("id") wardrobeId: Long): Completable

    @PUT("/wardrobe/{id}")
    fun update(@Path("id") wardrobeId: Long, @Body wardrobe: Wardrobe): Completable
}