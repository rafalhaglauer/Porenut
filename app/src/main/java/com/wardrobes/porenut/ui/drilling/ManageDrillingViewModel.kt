package com.wardrobes.porenut.ui.drilling

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.wardrobes.porenut.R
import com.wardrobes.porenut.data.drilling.DrillingRepository
import com.wardrobes.porenut.data.drilling.DrillingRestRepository
import com.wardrobes.porenut.domain.Drilling
import com.wardrobes.porenut.domain.UNDEFINED_ID
import com.wardrobes.porenut.ui.element.DrillingViewEntity
import com.wardrobes.porenut.ui.vo.DefaultMeasureFormatter
import com.wardrobes.porenut.ui.vo.MeasureFormatter
import com.wardrobes.porenut.ui.wardrobe.Result

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
            else fetchDetails(Result.COPIED)
        }

    fun manage(viewEntity: DrillingViewEntity) {
        if (drillingId == UNDEFINED_ID) add(viewEntity)
        else update(viewEntity)
    }

    fun delete() {
//        drillingRepository.delete(drillingId)
//                .fetchStateFullModel(
//                        onLoading = { createLoadingState() },
//                        onSuccess = { createResultState() },
//                        onError = { createErrorState(it) }
//                )
    }

    fun copy(viewEntity: DrillingViewEntity) {
//        drillingRepository.create(elementId, viewEntity.toDrilling())
//                .fetchStateFullModel(
//                        onLoading = { createLoadingState() },
//                        onSuccess = { drillingId = it },
//                        onError = { createErrorState(it) }
//                )
    }

    private fun add(viewEntity: DrillingViewEntity) {
//        drillingRepository.create(elementId, viewEntity.toDrilling())
//                .fetchStateFullModel(
//                        onLoading = { createLoadingState() },
//                        onSuccess = { createResultState() },
//                        onError = { createErrorState(it) }
//                )
    }

    private fun update(viewEntity: DrillingViewEntity) {
//        drillingRepository.update(drillingId, viewEntity.toDrilling())
//                .fetchStateFullModel(
//                        onLoading = { createLoadingState() },
//                        onSuccess = { createResultState() },
//                        onError = { createErrorState(it) }
//                )
    }

    private fun fetchDetails(result: Result? = null) {
//        drillingRepository.get(drillingId)
//                .fetchStateFullModel(
//                        onLoading = { createLoadingState() },
//                        onSuccess = { createDetailsState(it, result) },
//                        onError = { createErrorState(it) }
//                )
    }

    private fun createLoadingState() {
        viewState.updateValue(ManageDrillingViewState(isLoading = true))
    }

    private fun createDetailsState(drilling: Drilling, result: Result? = null) {
        viewState.updateValue(ManageDrillingViewState(viewEntity = drilling.toViewEntity(), btnTextMessage = R.string.l_save, isManageBtnVisible = true, result = result))
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

//    private fun DrillingViewEntity.toDrilling() = Drilling(
//            id = drillingId,
//            xPosition = measureFormatter.toFloat(xPosition),
//            yPosition = measureFormatter.toFloat(yPosition),
//            diameter = measureFormatter.toFloat(diameter),
//            depth = measureFormatter.toFloat(depth)
//    )

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