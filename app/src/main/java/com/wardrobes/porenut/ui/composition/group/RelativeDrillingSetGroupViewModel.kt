package com.wardrobes.porenut.ui.composition.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.drilling.set.RelativeDrillingSetRepository
import com.wardrobes.porenut.data.drilling.set.RelativeDrillingSetRestRepository
import com.wardrobes.porenut.domain.RelativeDrillingSet
import com.wardrobes.porenut.ui.common.Event
import com.wardrobes.porenut.ui.common.extension.updateValue
import com.wardrobes.porenut.ui.element.composition.group.ElementDrillingSetCompositionHeader
import com.wardrobes.porenut.ui.element.composition.group.ElementDrillingSetCompositionItem
import com.wardrobes.porenut.ui.element.composition.group.ElementDrillingSetCompositionViewEntity

class RelativeDrillingSetGroupViewModel(
    private val relativeDrillingCompositionRepository: RelativeDrillingSetRepository = RelativeDrillingSetRestRepository
) : ViewModel() {
    val viewState: LiveData<RelativeCompositionGroupViewState> = MutableLiveData()
    val errorMessageEvent: LiveData<Event<String>> = MutableLiveData()

    fun fetchDrillingSets() {
        relativeDrillingCompositionRepository.getAll()
            .fetchStateFullModel(
                onLoading = { createLoadingState() },
                onSuccess = { createSuccessState(it) },
                onError = { createErrorState(it) }
            )
    }

    private fun createLoadingState() {
        updateState(RelativeCompositionGroupViewState(isLoading = true))
    }

    private fun createSuccessState(drillingSets: List<RelativeDrillingSet>) {
        val viewEntities = mutableListOf<ElementDrillingSetCompositionItem>()
        drillingSets.groupBy { it.tag }
            .forEach { key, value ->
                viewEntities.add(ElementDrillingSetCompositionHeader(key))
                value.forEach { viewEntities.add(ElementDrillingSetCompositionViewEntity(it.id ?: 0L, it.name.substringBeforeLast(" |"))) }
            }
        updateState(RelativeCompositionGroupViewState(drillingSets = viewEntities))
    }

    private fun createErrorState(errorMessage: String) {
        errorMessageEvent.updateValue(Event(errorMessage))
        updateState(RelativeCompositionGroupViewState(isLoading = false))
    }

    private fun updateState(state: RelativeCompositionGroupViewState) {
        (viewState as MutableLiveData).value = state
    }
}

class RelativeCompositionGroupViewState(
    val isLoading: Boolean = false,
    val drillingSets: List<ElementDrillingSetCompositionItem> = emptyList()
)