package com.wardrobes.porenut.api.data

import com.wardrobes.porenut.domain.Drilling
import com.wardrobes.porenut.domain.DrillingLight
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.*

interface DrillingInterface {

    @GET("/drilling/all/{elementId}")
    fun getAll(@Path("elementId") elementId: Long): Observable<List<Drilling>>

    @GET("/drilling/{id}")
    fun get(@Path("id") drillingId: Long): Observable<Drilling>

    @POST("/drilling")
    fun add(@Body drilling: DrillingLight): Observable<Long>

    @DELETE("/drilling/{id}")
    fun delete(@Path("id") drillingId: Long): Completable

    @PUT("/drilling/{id}")
    fun update(@Path("id") drillingId: Long, @Body drilling: DrillingLight): Completable
}