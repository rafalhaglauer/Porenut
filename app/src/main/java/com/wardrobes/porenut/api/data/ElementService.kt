package com.wardrobes.porenut.api.data

import com.wardrobes.porenut.domain.Element
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.*

interface ElementService {

    @GET("/element/all/{wardrobeId}")
    fun getAll(@Path("wardrobeId") wardrobeId: String): Observable<List<Element>>

    @GET("/element/{id}")
    fun get(@Path("id") elementId: Long): Observable<Element>

    @POST("/element/{wardrobeId}")
    fun add(@Body element: Element, @Path("wardrobeId") wardrobeId: String): Completable

    @PUT("/element/{id}")
    fun update(@Path("id") elementId: Long, @Body element: Element): Completable

    @DELETE("/element/{id}")
    fun delete(@Path("id") elementId: Long): Completable
}





