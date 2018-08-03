package com.wardrobes.porenut.ui.element

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.drilling.DrillingRepository
import com.wardrobes.porenut.data.drilling.DrillingRestRepository
import com.wardrobes.porenut.domain.Drilling
import com.wardrobes.porenut.domain.UNDEFINED_ID
import com.wardrobes.porenut.ui.vo.DefaultMeasureFormatter
import com.wardrobes.porenut.ui.vo.MeasureFormatter

class DrillingGroupViewModel(
        private val drillingRepository: DrillingRepository = DrillingRestRepository,
        private val measureFormatter: MeasureFormatter = DefaultMeasureFormatter
) : ViewModel() {
    val viewState: LiveData<DrillingGroupViewState> = MutableLiveData()

    var elementId: Long = UNDEFINED_ID
        set(value) {
            field = value
            fetchDrillings()
        }

    private fun fetchDrillings() {
        drillingRepository.get(elementId)
                .fetchStateFullModel(
                        onLoading = { createLoadingState() },
                        onSuccess = { createSuccessState(it) },
                        onError = { createErrorState(it) }
                )
    }

    private fun createLoadingState() {
        updateState(DrillingGroupViewState(isLoading = true))
    }

    private fun createSuccessState(drillings: List<Drilling>) {
        updateState(DrillingGroupViewState(viewEntities = drillings.toViewEntities()))
    }

    private fun createErrorState(errorMessage: String?) {
        updateState(DrillingGroupViewState(errorMessage = errorMessage))
    }

    private fun updateState(state: DrillingGroupViewState) {
        (viewState as MutableLiveData).value = state
    }

    private fun List<Drilling>.toViewEntities() = map { it.toViewEntity() }

    private fun Drilling.toViewEntity() = DrillingViewEntity(
            xPosition = xPosition.formattedValue,
            yPosition = yPosition.formattedValue,
            diameter = diameter.formattedValue,
            depth = depth.formattedValue
    )

    private val Float.formattedValue: String
        get() = measureFormatter.format(this)
}

class DrillingGroupViewState(
        val isLoading: Boolean = false,
        val viewEntities: List<DrillingViewEntity> = emptyList(),
        val errorMessage: String? = null
)

data class DrillingViewEntity(
        val xPosition: String,
        val yPosition: String,
        val diameter: String,
        val depth: String)