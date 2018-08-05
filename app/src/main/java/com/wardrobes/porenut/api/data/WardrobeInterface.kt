package com.wardrobes.porenut.api.data

import com.wardrobes.porenut.data.wardrobe.WardrobeRequest
import com.wardrobes.porenut.domain.Wardrobe
import io.reactivex.Observable
import retrofit2.http.*

interface WardrobeInterface {

    @GET("/wardrobe")
    fun getAll(@Query("creationType") wardrobeCreationType: Wardrobe.CreationType): Observable<List<Wardrobe>>

    @GET("/wardrobe/{id}")
    fun get(@Path("id") wardrobeId: Long): Observable<Wardrobe>

    @DELETE("/wardrobe/{id}")
    fun remove(@Path("id") wardrobeId: Long): Observable<Any>

    @POST("/wardrobe")
    fun add(@Body wardrobeRequest: WardrobeRequest, @Query("creationType") wardrobeCreationType: Wardrobe.CreationType): Observable<Long>

    @PUT("/wardrobe/{id}")
    fun update(@Path("id") wardrobeId: Long, @Body wardrobe: WardrobeRequest): Observable<Long>

    @POST("/wardrobe/copy")
    fun copy(@Query("id") wardrobeId: Long, @Query("symbol") symbol: String): Observable<Long>
}