package com.wardrobes.porenut.ui.wardrobe.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.wardrobe.WardrobeRepository
import com.wardrobes.porenut.data.wardrobe.WardrobeRestRepository
import com.wardrobes.porenut.ui.common.Event
import com.wardrobes.porenut.ui.common.extension.updateValue

class WardrobeDashboardViewModel(private val wardrobeRepository: WardrobeRepository = WardrobeRestRepository) : ViewModel() {
    val loadingState: LiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    val messageEvent: LiveData<Event<String>> = MutableLiveData()
    val navigateUpEvent: LiveData<Event<Unit>> = MutableLiveData()

    var wardrobeId: Long? = null

    fun deleteWardrobe() {
        wardrobeId?.also { id ->
            wardrobeRepository.delete(id)
                .fetchStateFullModel(
                    onLoading = { loadingState.updateValue(true) },
                    onSuccess = {
                        messageEvent.updateValue(Event("Pomyślnie usunięto szafę!"))
                        navigateUpEvent.updateValue(Event(Unit))
                    },
                    onError = {
                        messageEvent.updateValue(Event(it))
                        loadingState.updateValue(false)
                    }
                )
        }
    }
}