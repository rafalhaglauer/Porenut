package com.wardrobes.porenut.api.data

import com.wardrobes.porenut.domain.ElementPattern
import com.wardrobes.porenut.domain.WardrobePattern
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.*

interface ElementPatternService {

    @GET("/pattern/wardrobe/{wardrobeId}/elements")
    fun getAll(@Path("wardrobeId") wardrobePatternId: String): Observable<List<ElementPattern>>

    @GET("/pattern/element/{id}")
    fun get(@Path("id") patternId: String): Observable<ElementPattern>

    @POST("/pattern/wardrobe/{wardrobeId}/elements")
    fun add(@Path("wardrobeId") wardrobePatternId: String, @Body elementPattern: ElementPattern): Completable

    @PUT("/pattern/element/{id}")
    fun update(@Path("id") patternId: String, @Body elementPattern: ElementPattern): Completable

    @DELETE("/pattern/element/{id}")
    fun delete(@Path("id") patternId: String): Completable

}