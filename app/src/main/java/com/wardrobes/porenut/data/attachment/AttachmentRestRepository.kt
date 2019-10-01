package com.wardrobes.porenut.data.attachment

import com.wardrobes.porenut.api.base.BaseProvider
import com.wardrobes.porenut.api.data.AttachmentService
import io.reactivex.Completable
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import java.io.File

object AttachmentRestRepository : AttachmentRepository {
    private val service = BaseProvider.retrofit.create(AttachmentService::class.java)

    override fun uploadPhoto(wardrobeId: String, file: File): Completable {
        val requestFile = RequestBody.create(MediaType.parse(file.path), file)
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
        return service.uploadPhoto(body, wardrobeId)
    }

    override fun getPhotoUrls(wardrobeId: String): Observable<List<String>> = service.getPhotoUrls(wardrobeId)

    override fun uploadModel(wardrobeId: String, file: File): Completable {
        val requestFile = RequestBody.create(MediaType.parse(file.path), file)
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
        return service.uploadModel(body, wardrobeId)
    }

    override fun getModel(wardrobeId: String): Observable<ResponseBody> = service.getModel(wardrobeId)
}