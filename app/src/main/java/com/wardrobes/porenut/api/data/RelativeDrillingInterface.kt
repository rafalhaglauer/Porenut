package com.wardrobes.porenut.api.data

import com.wardrobes.porenut.domain.RelativeDrilling
import com.wardrobes.porenut.domain.RelativeDrillingLight
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.*

interface RelativeDrillingInterface {

    @GET("/drilling/relative/all/{relativeDrillingCompositionId}")
    fun getAll(@Path("relativeDrillingCompositionId") compositionId: Long): Observable<List<RelativeDrilling>>

    @GET("/drilling/relative/{id}")
    fun get(@Path("id") drillingId: Long): Observable<RelativeDrilling>

    @POST("/drilling/relative")
    fun add(@Body relativeDrilling: RelativeDrillingLight): Observable<Long>

    @PUT("/drilling/relative/{id}")
    fun update(@Path("id") id: Long, @Body relativeDrilling: RelativeDrillingLight): Completable

    @DELETE("/drilling/relative/{id}")
    fun delete(@Path("id") id: Long): Completable
}