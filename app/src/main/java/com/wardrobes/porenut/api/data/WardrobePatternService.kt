package com.wardrobes.porenut.api.data

import com.wardrobes.porenut.domain.WardrobePattern
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.*

interface WardrobePatternService {

    @GET("/pattern/wardrobe")
    fun getAll(): Observable<List<WardrobePattern>>

    @GET("/pattern/wardrobe/{id}")
    fun get(@Path("id") patternId: String): Observable<WardrobePattern>

    @POST("/pattern/wardrobe")
    fun add(@Body wardrobePattern: WardrobePattern): Completable

    @PUT("/pattern/wardrobe/{id}")
    fun update(@Path("id") patternId: String, @Body wardrobePattern: WardrobePattern): Completable

}