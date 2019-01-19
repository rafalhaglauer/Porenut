package com.wardrobes.porenut.data.attachment

import io.reactivex.Completable
import io.reactivex.Observable
import java.io.File

interface AttachmentRepository {

    fun uploadPhoto(wardrobeId: Long, file: File): Completable

    fun getPhotoUrls(wardrobeId: Long): Observable<List<String>>
}