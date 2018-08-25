package com.wardrobes.porenut.ui.relative.composition

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.relative.RelativeDrillingCompositionRepository
import com.wardrobes.porenut.data.relative.RelativeDrillingCompositionRestRepository
import com.wardrobes.porenut.domain.RelativeDrillingComposition

class RelativeCompositionGroupViewModel(
    private val relativeDrillingCompositionRepository: RelativeDrillingCompositionRepository = RelativeDrillingCompositionRestRepository
) : ViewModel() {
    val viewState: LiveData<RelativeCompositionGroupViewState> = MutableLiveData()

    private var compositions: List<RelativeDrillingComposition> = emptyList()

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