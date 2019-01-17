package com.wardrobes.porenut.ui.wardrobe.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.api.base.BaseProvider
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.attachment.AttachmentRepository
import com.wardrobes.porenut.data.attachment.AttachmentRestRepository
import com.wardrobes.porenut.ui.extension.updateValue
import com.wardrobes.porenut.ui.vo.Event

class WardrobeGalleryViewModel(
    private val attachmentRepository: AttachmentRepository = AttachmentRestRepository
) : ViewModel() {
    val viewState: LiveData<WardrobeGalleryViewState> = MutableLiveData()
    val errorState: LiveData<Event<String>> = MutableLiveData()
    val photoUrls: List<String>
        get() = viewState.value?.photoUrls ?: emptyList()

    var wardrobeId: Long? = null
        set(value) {
            field = value?.also { fetchPhotos(it) }
        }

    private fun fetchPhotos(wardrobeId: Long) {
        attachmentRepository.getPhotoUrls(wardrobeId)
            .fetchStateFullModel(
                onLoading = { viewState.updateValue(WardrobeGalleryViewState(isLoading = true)) },
                onSuccess = { viewState.updateValue(WardrobeGalleryViewState(photoUrls = it.map { "${BaseProvider.retrofit.baseUrl()}/$it" })) },
                onError = { errorState.updateValue(Event(it)) }
            )
    }
}

class WardrobeGalleryViewState(
    val isLoading: Boolean = false,
    val photoUrls: List<String> = emptyList()
)