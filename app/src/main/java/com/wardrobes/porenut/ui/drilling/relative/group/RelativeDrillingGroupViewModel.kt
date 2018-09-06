package com.wardrobes.porenut.ui.drilling.relative.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.relative.RelativeDrillingRepository
import com.wardrobes.porenut.data.relative.RelativeDrillingRestRepository
import com.wardrobes.porenut.domain.RelativeDrilling

class RelativeDrillingGroupViewModel(
    private val relativeDrillingRepository: RelativeDrillingRepository = RelativeDrillingRestRepository
) : ViewModel() {
    val viewState: LiveData<RelativeDrillingGroupViewState> = MutableLiveData()

    var relativeCompositionId: Long? = null
        set(value) {
            field = value
            getDrillingGroup()
        }

    private var relativeDrillingGroup: List<RelativeDrilling> = emptyList()

    fun refresh() {
        getDrillingGroup()
    }

    private fun getDrillingGroup() {
        relativeCompositionId?.also { id ->
            relativeDrillingRepository.getAll(id)
                .fetchStateFullModel(
                    onLoading = { createLoadingState() },
                    onSuccess = {
                        relativeDrillingGroup = it
                        createSuccessState()
                    },
                    onError = { createErrorState(it) }
                )
        }
    }

    private fun createLoadingState() {
        updateState(RelativeDrillingGroupViewState(isLoading = true))
    }

    private fun createSuccessState() {
        updateState(RelativeDrillingGroupViewState(drillingNames = relativeDrillingGroup.map { it.name }))
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