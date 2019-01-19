package com.wardrobes.porenut.api.data

import io.reactivex.Completable
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

interface AttachmentService {

    @Multipart
    @POST("/gallery/{wardrobeId}/uploadPhoto")
    fun uploadPhoto(@Part file: MultipartBody.Part, @Path("wardrobeId") wardrobeId: Long): Completable

    @GET("/gallery/{wardrobeId}")
    fun getPhotoUrls(@Path("wardrobeId") wardrobeId: Long): Observable<List<String>>
}