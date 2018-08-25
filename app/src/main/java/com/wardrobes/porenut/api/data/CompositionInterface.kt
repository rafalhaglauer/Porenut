package com.wardrobes.porenut.api.data

import com.wardrobes.porenut.domain.ReferenceElementRelativeDrillingComposition
import com.wardrobes.porenut.domain.ReferenceElementRelativeDrillingCompositionLight
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CompositionInterface {

    @GET("/composition/{elementId}")
    fun getAll(@Path("elementId") elementId: Long): Observable<List<ReferenceElementRelativeDrillingComposition>>

    @POST("/composition")
    fun add(@Body composition: ReferenceElementRelativeDrillingCompositionLight): Observable<Unit>
}