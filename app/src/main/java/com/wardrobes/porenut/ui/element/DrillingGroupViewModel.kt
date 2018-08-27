package com.wardrobes.porenut.ui.element

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.drilling.DrillingRepository
import com.wardrobes.porenut.data.drilling.DrillingRestRepository
import com.wardrobes.porenut.domain.Drilling
import com.wardrobes.porenut.domain.UNDEFINED_ID
import com.wardrobes.porenut.domain.Wardrobe
import com.wardrobes.porenut.ui.vo.DefaultMeasureFormatter
import com.wardrobes.porenut.ui.vo.MeasureFormatter

class DrillingGroupViewModel(
    private val drillingRepository: DrillingRepository = DrillingRestRepository,
    private val measureFormatter: MeasureFormatter = DefaultMeasureFormatter
) : ViewModel() {
    val viewState: LiveData<DrillingGroupViewState> = MutableLiveData()

    var wardrobeId: Long = UNDEFINED_ID

    var elementId: Long = UNDEFINED_ID
        set(value) {
            field = value
            fetchDrillings()
        }

    var creationType: Wardrobe.CreationType? = null

    private var drillings: List<Drilling> = emptyList()

    fun refresh() {
        fetchDrillings()
    }

    private fun fetchDrillings() {
        drillingRepository.getAll(elementId)
            .fetchStateFullModel(
                onLoading = { createLoadingState() },
                onSuccess = {
                    drillings = it
                    createSuccessState(it)
                },
                onError = { createErrorState(it) }
            )
    }

    private fun createLoadingState() {
        updateState(DrillingGroupViewState(isLoading = true))
    }

    private fun createSuccessState(drillings: List<Drilling>) {
        updateState(
            DrillingGroupViewState(
                viewEntities = drillings.toViewEntities(),
                isAddDrillingBtnVisible = creationType == Wardrobe.CreationType.CUSTOM
            )
        )
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

    fun getId(viewEntity: DrillingViewEntity): Long =
        drillings.first { it.toViewEntity() == viewEntity }.id

    private val Float.formattedValue: String
        get() = measureFormatter.format(this)
}

class DrillingGroupViewState(
    val isLoading: Boolean = false,
    val viewEntities: List<DrillingViewEntity> = emptyList(),
    val errorMessage: String? = null,
    val isAddDrillingBtnVisible: Boolean = false
)

data class DrillingViewEntity(
    val xPosition: String,
    val yPosition: String,
    val diameter: String,
    val depth: String
)