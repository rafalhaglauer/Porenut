package com.wardrobes.porenut.data.attachment

import io.reactivex.Completable
import io.reactivex.Observable
import okhttp3.ResponseBody
import java.io.File

interface AttachmentRepository {

    fun uploadPhoto(wardrobeId: String, file: File): Completable

    fun getPhotoUrls(wardrobeId: String): Observable<List<String>>

    fun uploadModel(wardrobeId: String, file: File): Completable

    fun getModel(wardrobeId: String): Observable<ResponseBody>
}