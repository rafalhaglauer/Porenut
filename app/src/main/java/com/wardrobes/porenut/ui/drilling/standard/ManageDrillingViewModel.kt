package com.wardrobes.porenut.ui.drilling.standard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.R
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.drilling.DrillingRepository
import com.wardrobes.porenut.data.drilling.DrillingRestRepository
import com.wardrobes.porenut.domain.Drilling
import com.wardrobes.porenut.domain.DrillingLight
import com.wardrobes.porenut.ui.vo.DefaultMeasureFormatter
import com.wardrobes.porenut.ui.vo.MeasureFormatter
import com.wardrobes.porenut.ui.vo.Result
import com.wardrobes.porenut.ui.vo.UNDEFINED_ID

class ManageDrillingViewModel(
    private val drillingRepository: DrillingRepository = DrillingRestRepository,
    private val measureFormatter: MeasureFormatter = DefaultMeasureFormatter
) : ViewModel() {
    val viewState: LiveData<ManageDrillingViewState> = MutableLiveData()

    var elementId: Long = UNDEFINED_ID

    var drillingId: Long = UNDEFINED_ID
        set(value) {
            field = value
            if (value == UNDEFINED_ID) viewState.updateValue(ManageDrillingViewState())
            else fetchDetails()
        }

    fun manage(viewEntity: DrillingViewEntity) {
        if (drillingId == UNDEFINED_ID) add(viewEntity)
        else update(viewEntity)
    }

    fun delete() {
        drillingRepository.delete(drillingId)
            .fetchStateFullModel(
                onLoading = { createLoadingState() },
                onSuccess = { createResultState() },
                onError = { createErrorState(it) }
            )
    }

    private fun add(viewEntity: DrillingViewEntity) {
        drillingRepository.add(viewEntity.toDrilling())
            .fetchStateFullModel(
                onLoading = { createLoadingState() },
                onSuccess = { createResultState() },
                onError = { createErrorState(it) }
            )
    }

    private fun update(viewEntity: DrillingViewEntity) {
        drillingRepository.update(drillingId, viewEntity.toDrilling())
            .fetchStateFullModel(
                onLoading = { createLoadingState() },
                onSuccess = { createResultState() },
                onError = { createErrorState(it) }
            )
    }

    private fun fetchDetails() {
        drillingRepository.get(drillingId)
            .fetchStateFullModel(
                onLoading = { createLoadingState() },
                onSuccess = { createDetailsState(it) },
                onError = { createErrorState(it) }
            )
    }

    private fun createLoadingState() {
        viewState.updateValue(ManageDrillingViewState(isLoading = true))
    }

    private fun createDetailsState(drilling: Drilling) {
        viewState.updateValue(
            ManageDrillingViewState(
                viewEntity = drilling.toViewEntity(),
                btnTextMessage = R.string.l_save,
                isManageBtnVisible = true
            )
        )
    }

    private fun createResultState() {
        viewState.updateValue(ManageDrillingViewState(result = Result.MODIFIED))
    }

    private fun createErrorState(errorMessage: String?) {
        viewState.updateValue(ManageDrillingViewState(errorMessage = errorMessage))
    }

    private fun LiveData<ManageDrillingViewState>.updateValue(viewState: ManageDrillingViewState) {
        (this as MutableLiveData).value = viewState
    }

    private fun DrillingViewEntity.toDrilling() = DrillingLight(
        xPosition = measureFormatter.toFloat(xPosition),
        yPosition = measureFormatter.toFloat(yPosition),
        diameter = measureFormatter.toFloat(diameter),
        depth = measureFormatter.toFloat(depth),
        elementId = elementId
    )

    private fun Drilling.toViewEntity() = DrillingViewEntity(
        xPosition = xPosition.formattedValue,
        yPosition = yPosition.formattedValue,
        diameter = diameter.formattedValue,
        depth = depth.formattedValue
    )

    private val Float.formattedValue: String
        get() = measureFormatter.format(this)
}

class ManageDrillingViewState(
    val isLoading: Boolean = false,
    val viewEntity: DrillingViewEntity? = null,
    val errorMessage: String? = null,
    val result: Result? = null,
    val btnTextMessage: Int = R.string.l_add,
    val isManageBtnVisible: Boolean = false
)

data class DrillingViewEntity(
    val xPosition: String,
    val yPosition: String,
    val diameter: String,
    val depth: String
)