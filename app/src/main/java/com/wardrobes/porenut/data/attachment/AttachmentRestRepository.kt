package com.wardrobes.porenut.data.attachment

import com.wardrobes.porenut.api.base.BaseProvider
import com.wardrobes.porenut.api.data.AttachmentService
import io.reactivex.Completable
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

object AttachmentRestRepository : AttachmentRepository {
    private val service = BaseProvider.retrofit.create(AttachmentService::class.java)

    override fun uploadPhoto(wardrobeId: Long, file: File): Completable {
        val requestFile = RequestBody.create(MediaType.parse(file.path), file)
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
        return service.uploadPhoto(body, wardrobeId)
    }

    override fun getPhotoUrls(wardrobeId: Long): Observable<List<String>> = service.getPhotoUrls(wardrobeId)

}