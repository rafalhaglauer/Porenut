package com.wardrobes.porenut.ui.composition.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.relative.RelativeDrillingSetRepository
import com.wardrobes.porenut.data.relative.RelativeDrillingSetRestRepository
import com.wardrobes.porenut.domain.RelativeDrillingSet

class RelativeCompositionGroupViewModel(
    private val relativeDrillingCompositionRepository: RelativeDrillingSetRepository = RelativeDrillingSetRestRepository
) : ViewModel() {
    val viewState: LiveData<RelativeCompositionGroupViewState> = MutableLiveData()

    private var compositions: List<RelativeDrillingSet> = emptyList()

    fun startObserving() {
        getCompositions()
    }

    fun refresh() {
        getCompositions()
    }

    fun getRelativeCompositionId(compositionName: String) =
        compositions.first { it.name == compositionName }.id

    private fun getCompositions() {
        relativeDrillingCompositionRepository.getAll()
            .fetchStateFullModel(
                onLoading = { createLoadingState() },
                onSuccess = {
                    compositions = it
                    createSuccessState()
                },
                onError = { createErrorState(it) }
            )
    }

    private fun createLoadingState() {
        updateState(RelativeCompositionGroupViewState(isLoading = true))
    }

    private fun createSuccessState() {
        updateState(RelativeCompositionGroupViewState(compositions = compositions.map { it.name }))
    }

    private fun createErrorState(errorMessage: String?) {
        updateState(RelativeCompositionGroupViewState(errorMessage = errorMessage))
    }

    private fun updateState(state: RelativeCompositionGroupViewState) {
        (viewState as MutableLiveData).value = state
    }
}

class RelativeCompositionGroupViewState(
    val isLoading: Boolean = false,
    val compositions: List<String> = emptyList(),
    val errorMessage: String? = null
)