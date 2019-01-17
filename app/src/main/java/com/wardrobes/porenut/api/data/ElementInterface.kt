package com.wardrobes.porenut.api.data

import com.wardrobes.porenut.domain.Element
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.*

interface ElementInterface {

    @GET("/element/all/{wardrobeId}")
    fun getAll(@Path("wardrobeId") wardrobeId: Long): Observable<List<Element>>

    @GET("/element/{id}")
    fun get(@Path("id") elementId: Long): Observable<Element>

    @POST("/element/{wardrobeId}")
    fun add(@Body element: Element, @Path("wardrobeId") wardrobeId: Long): Observable<Long>

    @PUT("/element/{id}")
    fun update(@Path("id") elementId: Long, @Body element: Element): Observable<Unit>

    @DELETE("/element/{id}")
    fun delete(@Path("id") elementId: Long): Completable
}