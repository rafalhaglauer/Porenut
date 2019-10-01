package com.wardrobes.porenut.api.data

import com.wardrobes.porenut.data.composition.ElementDrillingSetCompositionRequest
import com.wardrobes.porenut.domain.ElementDrillingSetComposition
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.*

interface ElementDrillingSetCompositionService {

    @GET("/composition/all/{elementId}")
    fun getAll(@Path("elementId") elementId: Long): Observable<List<ElementDrillingSetComposition>>

    @GET("/composition/{compositionId}")
    fun get(@Path("compositionId") compositionId: Long): Observable<ElementDrillingSetComposition>

    @DELETE("/composition/{compositionId}")
    fun delete(@Path("compositionId") compositionId: Long): Completable

    @PUT("/composition/{compositionId}")
    fun update(@Path("compositionId") compositionId: Long, @Body composition: ElementDrillingSetCompositionRequest): Completable

    @POST("/composition")
    fun add(@Body composition: ElementDrillingSetCompositionRequest): Completable
}