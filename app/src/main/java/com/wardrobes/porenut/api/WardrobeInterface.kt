package com.wardrobes.porenut.api

import com.wardrobes.porenut.model.Wardrobe
import io.reactivex.Flowable
import retrofit2.http.*

interface WardrobeInterface {

    @GET("/wardrobes")
    fun getAll(): Flowable<List<Wardrobe>>

    @GET("/wardrobes/{id}")
    fun get(@Path("id") wardrobeId: Long): Flowable<Wardrobe>

    @DELETE("/wardrobes/{id}")
    fun remove(@Path("id") wardrobeId: Long)

    @POST("/wardrobes")
    fun create(@Body wardrobe: Wardrobe)

    @PUT("/wardrobes/{id}")
    fun update(@Path("id") wardrobeId: Long, @Body wardrobe: Wardrobe)
}