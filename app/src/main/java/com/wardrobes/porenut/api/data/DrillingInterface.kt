package com.wardrobes.porenut.api.data

import com.wardrobes.porenut.domain.Drilling
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface DrillingInterface {

    @GET("/drilling/{elementId}")
    fun get(@Path("elementId") elementId: Long): Observable<List<Drilling>>
}