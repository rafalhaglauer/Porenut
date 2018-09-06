package com.wardrobes.porenut.api.data

import com.wardrobes.porenut.domain.ReferenceElementRelativeDrillingComposition
import com.wardrobes.porenut.domain.ReferenceElementRelativeDrillingCompositionLight
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.*

interface CompositionInterface {

    @GET("/composition/all/{elementId}")
    fun getAll(@Path("elementId") elementId: Long): Observable<List<ReferenceElementRelativeDrillingComposition>>

    @GET("/composition/{compositionId}")
    fun get(@Path("compositionId") compositionId: Long): Observable<ReferenceElementRelativeDrillingComposition>

    @DELETE("/composition/{compositionId}")
    fun delete(@Path("compositionId") compositionId: Long): Completable

    @PUT("/composition/{compositionId}")
    fun update(@Path("compositionId") compositionId: Long, @Body composition: ReferenceElementRelativeDrillingCompositionLight): Completable

    @POST("/composition")
    fun add(@Body composition: ReferenceElementRelativeDrillingCompositionLight): Completable
}