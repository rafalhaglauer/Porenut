package com.wardrobes.porenut.ui.wardrobe.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.api.base.BaseProvider
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.attachment.AttachmentRepository
import com.wardrobes.porenut.data.attachment.AttachmentRestRepository
import com.wardrobes.porenut.ui.common.Event
import com.wardrobes.porenut.ui.common.extension.updateValue
import java.io.File

class WardrobeGalleryViewModel(
    private val attachmentRepository: AttachmentRepository = AttachmentRestRepository
) : ViewModel() {
    val viewState: LiveData<WardrobeGalleryViewState> = MutableLiveData()
    val errorState: LiveData<Event<String>> = MutableLiveData()
    val photoUrls: List<String>
        get() = viewState.value?.photoUrls ?: emptyList()

    var wardrobeId: String? = null
        set(value) {
            field = value?.also { fetchPhotos(it) }
        }

    fun uploadPhoto(photo: File) {
        wardrobeId?.also { id ->
            attachmentRepository.uploadPhoto(id, photo).fetchStateFullModel(
                onLoading = { showLoading() },
                onSuccess = { fetchPhotos(id) },
                onError = { showError(it) }
            )
        }
    }

    private fun fetchPhotos(wardrobeId: String) {
        attachmentRepository.getPhotoUrls(wardrobeId)
            .fetchStateFullModel(
                onLoading = { showLoading() },
                onSuccess = { showGallery(it) },
                onError = { showError(it) }
            )
    }

    private fun showLoading() {
        viewState.updateValue(WardrobeGalleryViewState(isLoading = true))
    }

    private fun showGallery(urls: List<String>) {
        viewState.updateValue(WardrobeGalleryViewState(photoUrls = urls.map { "${BaseProvider.retrofit.baseUrl()}/$it" }))
    }

    private fun showError(message: String) {
        errorState.updateValue(Event(message))
    }
}

class WardrobeGalleryViewState(
    val isLoading: Boolean = false,
    val photoUrls: List<String> = emptyList()
)