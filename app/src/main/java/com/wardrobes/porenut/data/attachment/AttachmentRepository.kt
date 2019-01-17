package com.wardrobes.porenut.data.attachment

import io.reactivex.Observable

interface AttachmentRepository {

    fun getPhotoUrls(wardrobeId: Long): Observable<List<String>>
}