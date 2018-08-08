package com.wardrobes.porenut.api.data

import com.wardrobes.porenut.domain.Drilling
import io.reactivex.Observable
import retrofit2.http.*

interface DrillingInterface {

    @GET("/drilling/all/{elementId}")
    fun getAll(@Path("elementId") elementId: Long): Observable<List<Drilling>>

    @GET("/drilling/{drillingId}")
    fun get(@Path("drillingId") drillingId: Long): Observable<Drilling>

    @POST("/drilling")
    fun create(@Query("elementId") elementId: Long, @Body drilling: Drilling): Observable<Long>

    @DELETE("/drilling/{drillingId}")
    fun delete(@Path("drillingId") drillingId: Long): Observable<Any>

    @PUT("/drilling/{drillingId}")
    fun update(@Path("drillingId") drillingId: Long, @Body drilling: Drilling): Observable<Any>
}