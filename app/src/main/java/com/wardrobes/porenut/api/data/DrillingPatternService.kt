package com.wardrobes.porenut.api.data

import com.wardrobes.porenut.domain.DrillingPattern
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.*

interface DrillingPatternService {

    @GET("/pattern/element/{elementId}/drillings")
    fun getAll(@Path("elementId") elementPatternId: String): Observable<List<DrillingPattern>>

    @GET("/pattern/drilling/{id}")
    fun get(@Path("id") patternId: String): Observable<DrillingPattern>

    @POST("/pattern/element/{elementId}/drillings")
    fun add(@Path("elementId") drillingPatternId: String, @Body drillingPattern: DrillingPattern): Completable

    @PUT("/pattern/drilling/{id}")
    fun update(@Path("id") patternId: String, @Body drillingPattern: DrillingPattern): Completable

    @DELETE("/pattern/drilling/{id}")
    fun delete(@Path("id") patternId: String): Completable

}