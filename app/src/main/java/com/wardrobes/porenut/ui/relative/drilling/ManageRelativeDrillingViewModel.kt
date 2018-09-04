package com.wardrobes.porenut.ui.relative.drilling

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.relative.RelativeDrillingRepository
import com.wardrobes.porenut.data.relative.RelativeDrillingRestRepository
import com.wardrobes.porenut.domain.CompositeOffset
import com.wardrobes.porenut.domain.RelativeDrillingLight
import com.wardrobes.porenut.ui.vo.Result

class ManageRelativeDrillingViewModel(
    private val relativeDrillingRepository: RelativeDrillingRepository = RelativeDrillingRestRepository
) : ViewModel() {
    val viewState: LiveData<ManageRelativeDrillingViewState> =
        MutableLiveData<ManageRelativeDrillingViewState>().apply {
            value = ManageRelativeDrillingViewState()
        }

    var relativeCompositionId: Long = -1

    fun add(
        name: String,
        xOffset: CompositeOffset,
        yOffset: CompositeOffset,
        diameter: Float,
        depth: Float
    ) {
        add(RelativeDrillingLight(name, xOffset, yOffset, diameter, depth, relativeCompositionId))
    }

    private fun add(drilling: RelativeDrillingLight) {
        relativeDrillingRepository.add(drilling)
            .fetchStateFullModel(
                onLoading = { createLoadingState() },
                onSuccess = { createResultState() },
                onError = { createErrorState(it) }
            )
    }

    private fun createLoadingState() {
        viewState.updateValue(ManageRelativeDrillingViewState(isLoading = true))
    }

    private fun createResultState() {
        viewState.updateValue(ManageRelativeDrillingViewState(result = Result.ADDED))
    }

    private fun createErrorState(errorMessage: String?) {
        viewState.updateValue(ManageRelativeDrillingViewState(errorMessage = errorMessage))
    }

    private fun LiveData<ManageRelativeDrillingViewState>.updateValue(viewState: ManageRelativeDrillingViewState) {
        (this as MutableLiveData).value = viewState
    }
}

class ManageRelativeDrillingViewState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val result: Result? = null
)