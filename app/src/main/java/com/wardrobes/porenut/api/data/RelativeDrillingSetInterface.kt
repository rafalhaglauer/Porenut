package com.wardrobes.porenut.api.data

import com.wardrobes.porenut.domain.RelativeDrillingSet
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.*

interface RelativeDrillingSetInterface {

    @GET("/drilling/relative/composition/all")
    fun getAll(): Observable<List<RelativeDrillingSet>>

    @GET("/drilling/relative/composition/{id}")
    fun get(@Path("id") compositionId: Long): Observable<RelativeDrillingSet>

    @POST("/drilling/relative/composition")
    fun add(@Body relativeDrillingSet: RelativeDrillingSet): Observable<Long>

    @DELETE("/drilling/relative/composition/{id}")
    fun delete(@Path("id") id: Long): Completable

    @PUT("/drilling/relative/composition/{id}")
    fun update(@Path("id") id: Long, @Body relativeDrillingSet: RelativeDrillingSet): Completable
}