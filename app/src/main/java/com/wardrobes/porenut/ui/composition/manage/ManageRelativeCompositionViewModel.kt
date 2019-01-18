package com.wardrobes.porenut.ui.composition.manage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.relative.RelativeDrillingSetRepository
import com.wardrobes.porenut.data.relative.RelativeDrillingSetRestRepository
import com.wardrobes.porenut.domain.RelativeDrillingSet
import com.wardrobes.porenut.ui.vo.Result

class ManageRelativeCompositionViewModel(
    private val relativeDrillingCompositionRepository: RelativeDrillingSetRepository = RelativeDrillingSetRestRepository
) : ViewModel() {
    val viewState: LiveData<ManageRelativeCompositionViewState> =
        MutableLiveData<ManageRelativeCompositionViewState>().apply {
            value = ManageRelativeCompositionViewState()
        }

    fun add(name: String) {
        relativeDrillingCompositionRepository.add(RelativeDrillingSet(name = name))
            .fetchStateFullModel(
                onLoading = { createLoadingState() },
                onSuccess = { createResultState() },
                onError = { createErrorState(it) }
            )
    }

    private fun createLoadingState() {
        viewState.updateValue(ManageRelativeCompositionViewState(isLoading = true))
    }

    private fun createResultState() {
        viewState.updateValue(ManageRelativeCompositionViewState(result = Result.ADDED))
    }

    private fun createErrorState(errorMessage: String?) {
        viewState.updateValue(ManageRelativeCompositionViewState(errorMessage = errorMessage))
    }

    private fun LiveData<ManageRelativeCompositionViewState>.updateValue(viewState: ManageRelativeCompositionViewState) {
        (this as MutableLiveData).value = viewState
    }
}

class ManageRelativeCompositionViewState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val result: Result? = null
)