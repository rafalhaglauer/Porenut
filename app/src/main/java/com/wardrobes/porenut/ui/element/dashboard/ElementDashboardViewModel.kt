package com.wardrobes.porenut.ui.element.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.element.ElementRepository
import com.wardrobes.porenut.data.element.ElementRestRepository
import com.wardrobes.porenut.ui.extension.updateValue
import com.wardrobes.porenut.ui.vo.Event

class ElementDashboardViewModel(private val elementRepository: ElementRepository = ElementRestRepository) : ViewModel() {
    val loadingState: LiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    val messageEvent: LiveData<Event<String>> = MutableLiveData()
    val navigateUpEvent: LiveData<Event<Unit>> = MutableLiveData()

    fun deleteElement(elementId: Long) {
        elementRepository.delete(elementId)
            .fetchStateFullModel(
                onLoading = { loadingState.updateValue(true) },
                onSuccess = {
                    messageEvent.updateValue(Event("Pomyślnie usunięto element!"))
                    navigateUpEvent.updateValue(Event(Unit))
                },
                onError = {
                    messageEvent.updateValue(Event(it))
                }
            )
    }
}