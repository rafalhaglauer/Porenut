package com.wardrobes.porenut.data.attachment

import com.wardrobes.porenut.api.base.BaseProvider
import com.wardrobes.porenut.api.data.AttachmentInterface
import io.reactivex.Observable

object AttachmentRestRepository: AttachmentRepository {
    private val service = BaseProvider.retrofit.create(AttachmentInterface::class.java)

    override fun getPhotoUrls(wardrobeId: Long): Observable<List<String>> = service.getPhotoUrls(wardrobeId)

}