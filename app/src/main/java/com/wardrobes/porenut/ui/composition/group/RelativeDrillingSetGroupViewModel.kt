package com.wardrobes.porenut.ui.composition.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.relative.RelativeDrillingSetRepository
import com.wardrobes.porenut.data.relative.RelativeDrillingSetRestRepository
import com.wardrobes.porenut.domain.RelativeDrillingSet
import com.wardrobes.porenut.ui.extension.updateValue
import com.wardrobes.porenut.ui.vo.Event

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
        updateState(RelativeCompositionGroupViewState(drillingSets = drillingSets))
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
    val drillingSets: List<RelativeDrillingSet> = emptyList()
)