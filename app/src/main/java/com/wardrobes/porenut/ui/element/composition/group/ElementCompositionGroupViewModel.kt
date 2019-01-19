package com.wardrobes.porenut.ui.element.composition.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.composition.ElementDrillingSetCompositionRepository
import com.wardrobes.porenut.data.composition.ElementDrillingSetCompositionRestRepository
import com.wardrobes.porenut.domain.ElementDrillingSetComposition
import com.wardrobes.porenut.ui.common.Event
import com.wardrobes.porenut.ui.common.extension.updateValue

class ElementCompositionGroupViewModel(private val compositionRepository: ElementDrillingSetCompositionRepository = ElementDrillingSetCompositionRestRepository) :
    ViewModel() {
    val viewState: LiveData<ElementCompositionViewState> = MutableLiveData()
    val errorEvent: LiveData<Event<String>> = MutableLiveData()

    var elementId: Long? = null
        set(value) {
            field = value?.also { showCompositionGroup(it) }
        }

    private fun showCompositionGroup(elementId: Long) {
        compositionRepository.getAll(elementId)
            .fetchStateFullModel(
                onLoading = { viewState.updateValue(ElementCompositionViewState(isLoading = true)) },
                onSuccess = { viewState.updateValue(ElementCompositionViewState(isEmptyListNotificationVisible = it.isEmpty(), compositions = it)) },
                onError = { errorEvent.updateValue(Event(it)) }
            )
    }
}

class ElementCompositionViewState(
    val isLoading: Boolean = false,
    val compositions: List<ElementDrillingSetComposition> = emptyList(),
    val isEmptyListNotificationVisible: Boolean = false
)