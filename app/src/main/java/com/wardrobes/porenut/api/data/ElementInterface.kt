package com.wardrobes.porenut.api.data

import com.wardrobes.porenut.domain.Element
import com.wardrobes.porenut.domain.ElementLight
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.*

interface ElementInterface {

    @GET("/element/all/{wardrobeId}")
    fun getAll(@Path("wardrobeId") wardrobeId: Long): Observable<List<Element>>

    @GET("/element/{id}")
    fun get(@Path("id") elementId: Long): Observable<Element>

    @POST("/element")
    fun add(@Body element: ElementLight): Observable<Long>

    @PUT("/element/{id}")
    fun update(@Path("id") elementId: Long, @Body element: ElementLight): Observable<Unit>

    @DELETE("/element/{id}")
    fun delete(@Path("id") elementId: Long): Completable

//    @POST("/element/copy")
//    fun copy(@Query("id") elementId: Long, @Query("name") name: String): Observable<Long>
}