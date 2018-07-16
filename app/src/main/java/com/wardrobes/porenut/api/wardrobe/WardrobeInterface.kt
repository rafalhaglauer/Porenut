package com.wardrobes.porenut.api.wardrobe

import com.wardrobes.porenut.data.wardrobe.WardrobeRequest
import com.wardrobes.porenut.domain.Wardrobe
import io.reactivex.Observable
import retrofit2.http.*

interface WardrobeInterface {

    @GET("/wardrobe")
    fun getAll(): Observable<List<Wardrobe>>

    @GET("/wardrobe/{id}")
    fun get(@Path("id") wardrobeId: Long): Observable<Wardrobe>

    @DELETE("/wardrobe/{id}")
    fun remove(@Path("id") wardrobeId: Long)

    @POST("/wardrobe")
    fun add(@Body wardrobeRequest: WardrobeRequest): Observable<Any>

    @PUT("/wardrobe/{id}")
    fun update(@Path("id") wardrobeId: Long, @Body wardrobe: Wardrobe)
}