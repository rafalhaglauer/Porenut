package com.wardrobes.porenut.api.data

import com.wardrobes.porenut.domain.RelativeDrilling
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.*

interface RelativeDrillingInterface {

    @GET("/drilling/relative/all/{drillingSetId}")
    fun getAll(@Path("drillingSetId") compositionId: Long): Observable<List<RelativeDrilling>>

    @GET("/drilling/relative/{id}")
    fun get(@Path("id") drillingId: Long): Observable<RelativeDrilling>

    @POST("/drilling/relative/{drillingSetId}")
    fun add(@Path("drillingSetId") drillingSetId: Long, @Body relativeDrilling: RelativeDrilling): Completable

    @PUT("/drilling/relative/{id}")
    fun update(@Path("id") id: Long, @Body relativeDrilling: RelativeDrilling): Completable

    @DELETE("/drilling/relative/{id}")
    fun delete(@Path("id") id: Long): Completable
}