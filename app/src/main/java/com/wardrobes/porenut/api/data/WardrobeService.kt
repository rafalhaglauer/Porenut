package com.wardrobes.porenut.api.data

import com.wardrobes.porenut.domain.Wardrobe
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.*

interface WardrobeService {

    @GET("/wardrobe")
    fun getAll(): Observable<List<Wardrobe>>

    @GET("/wardrobe/{id}")
    fun get(@Path("id") wardrobeId: String): Observable<Wardrobe>

    @POST("/wardrobe/{patternId}")
    fun add(@Body wardrobe: Wardrobe, @Path("patternId") patternId: String): Completable

    @DELETE("/wardrobe/{id}")
    fun delete(@Path("id") wardrobeId: String): Completable

    @PUT("/wardrobe/{id}")
    fun update(@Path("id") wardrobeId: String, @Body wardrobe: Wardrobe): Completable
}