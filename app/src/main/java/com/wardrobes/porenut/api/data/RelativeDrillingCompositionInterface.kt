package com.wardrobes.porenut.api.data

import com.wardrobes.porenut.domain.RelativeDrillingComposition
import com.wardrobes.porenut.domain.RelativeDrillingCompositionLight
import io.reactivex.Observable
import retrofit2.http.*

interface RelativeDrillingCompositionInterface {

    @GET("/drilling/relative/composition/all")
    fun getAll(): Observable<List<RelativeDrillingComposition>>

    @GET("/drilling/relative/composition/{id}")
    fun get(@Path("id") compositionId: Long): Observable<RelativeDrillingComposition>

    @POST("/drilling/relative/composition")
    fun add(@Body relativeDrillingComposition: RelativeDrillingCompositionLight): Observable<Long>

    @PUT("/drilling/relative/composition/{id}")
    fun update(@Path("id") id: Long, @Body relativeDrillingComposition: RelativeDrillingCompositionLight): Observable<Unit>
}