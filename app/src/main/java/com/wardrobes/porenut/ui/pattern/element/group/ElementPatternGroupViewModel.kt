package com.wardrobes.porenut.ui.pattern.element.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.pattern.element.ElementPatternRepository
import com.wardrobes.porenut.data.pattern.element.ElementPatternRestRepository
import com.wardrobes.porenut.domain.ElementPattern
import com.wardrobes.porenut.ui.common.Event
import com.wardrobes.porenut.ui.common.extension.updateValue

class ElementPatternGroupViewModel(
    private val elementPatternRepository: ElementPatternRepository = ElementPatternRestRepository
) : ViewModel() {

    val viewState: LiveData<ElementPatternGroupViewState> = MutableLiveData()
    val errorEvent: LiveData<Event<String>> = MutableLiveData()
    val showDetailsEvent: LiveData<Event<String>> = MutableLiveData()
    val showDrillingsEvent: LiveData<Event<String>> = MutableLiveData()

    private var patterns: List<ElementPattern> = emptyList()

    fun loadElements(wardrobePatternId: String) {
        elementPatternRepository.getAll(wardrobePatternId)
            .fetchStateFullModel(
                onLoading = { showLoading(true) },
                onSuccess = {
                    patterns = it
                    showPatterns(it)
                },
                onError = {
                    showLoading(false)
                    showError(it)
                }
            )
    }

    fun showDetails(viewEntity: ElementPatternViewEntity) {
        showLoading(true)
        showDetailsEvent.updateValue(Event(findElement(viewEntity).id))
    }

    fun showDrillings(viewEntity: ElementPatternViewEntity) {
        showLoading(true)
        showDrillingsEvent.updateValue(Event(findElement(viewEntity).id))
    }

    private fun showLoading(isLoading: Boolean) {
        viewState.updateValue(ElementPatternGroupViewState(isLoading = isLoading))
    }

    private fun showPatterns(elements: List<ElementPattern>) {
        viewState.updateValue(
            ElementPatternGroupViewState(
                isEmptyListNotificationVisible = elements.isEmpty(),
                viewEntities = map(elements)
            )
        )
    }

    private fun showError(message: String) {
        errorEvent.updateValue(Event(message))
    }

    private fun findElement(viewEntity: ElementPatternViewEntity) = patterns.first { it.name == viewEntity.name }

    private fun map(elements: List<ElementPattern>): List<ElementPatternViewEntity> = elements.map {
        ElementPatternViewEntity(it.name)
    }

}

class ElementPatternGroupViewState(
    val isLoading: Boolean = false,
    val viewEntities: List<ElementPatternViewEntity> = emptyList(),
    val isEmptyListNotificationVisible: Boolean = false
)

data class ElementPatternViewEntity(
    val name: String
)