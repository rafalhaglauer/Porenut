package com.wardrobes.porenut.ui.element.composition.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.composition.CompositionRepository
import com.wardrobes.porenut.data.composition.CompositionRestRepository
import com.wardrobes.porenut.ui.extension.updateValue
import com.wardrobes.porenut.ui.vo.Event

class ElementCompositionGroupViewModel(private val compositionRepository: CompositionRepository = CompositionRestRepository) : ViewModel() {
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
                onSuccess = { compositions ->
                    viewState.updateValue(
                        ElementCompositionViewState(isEmptyListNotificationVisible = compositions.isEmpty(), names = compositions.map {
                            it.drillingSet.name
                        })
                    )
                },
                onError = { errorEvent.updateValue(Event(it)) }
            )
    }
}

class ElementCompositionViewState(
    val isLoading: Boolean = false,
    val names: List<String> = emptyList(),
    val isEmptyListNotificationVisible: Boolean = false
)