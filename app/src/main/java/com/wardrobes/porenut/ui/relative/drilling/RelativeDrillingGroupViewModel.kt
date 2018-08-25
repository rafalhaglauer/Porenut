package com.wardrobes.porenut.ui.relative.drilling

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.relative.RelativeDrillingRepository
import com.wardrobes.porenut.data.relative.RelativeDrillingRestRepository
import com.wardrobes.porenut.domain.RelativeDrilling

class RelativeDrillingGroupViewModel(
    private val relativeDrillingRepository: RelativeDrillingRepository = RelativeDrillingRestRepository
) : ViewModel() {
    val viewState: LiveData<RelativeDrillingGroupViewState> = MutableLiveData()

    var relativeCompositionId: Long = -1
        set(value) {
            field = value
            getDrillings()
        }

    private var relativeDrillings: List<RelativeDrilling> = emptyList()

    fun refresh() {
        getDrillings()
    }

    private fun getDrillings() {
        relativeDrillingRepository.getAll(relativeCompositionId)
            .fetchStateFullModel(
                onLoading = { createLoadingState() },
                onSuccess = {
                    relativeDrillings = it
                    createSuccessState()
                },
                onError = { createErrorState(it) }
            )
    }

    private fun createLoadingState() {
        updateState(RelativeDrillingGroupViewState(isLoading = true))
    }

    private fun createSuccessState() {
        updateState(RelativeDrillingGroupViewState(drillingNames = relativeDrillings.map { it.name }))
    }

    private fun createErrorState(errorMessage: String?) {
        updateState(RelativeDrillingGroupViewState(errorMessage = errorMessage))
    }

    private fun updateState(state: RelativeDrillingGroupViewState) {
        (viewState as MutableLiveData).value = state
    }
}

class RelativeDrillingGroupViewState(
    val isLoading: Boolean = false,
    val drillingNames: List<String> = emptyList(),
    val errorMessage: String? = null
)