package com.wardrobes.porenut.ui.drilling.relative.manage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.R
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.relative.RelativeDrillingRepository
import com.wardrobes.porenut.data.relative.RelativeDrillingRestRepository
import com.wardrobes.porenut.domain.Offset
import com.wardrobes.porenut.domain.RelativeDrilling
import com.wardrobes.porenut.ui.vo.DefaultMeasureFormatter
import com.wardrobes.porenut.ui.vo.MeasureFormatter
import com.wardrobes.porenut.ui.vo.Result

class ManageRelativeDrillingViewModel(
    private val relativeDrillingRepository: RelativeDrillingRepository = RelativeDrillingRestRepository,
    private val measureFormatter: MeasureFormatter = DefaultMeasureFormatter
) : ViewModel() {
    val viewState: LiveData<ManageRelativeDrillingViewState> =
        MutableLiveData<ManageRelativeDrillingViewState>().apply {
            value = ManageRelativeDrillingViewState()
        }

    var relativeDrillingSetId: Long? = null

    var relativeDrillingId: Long? = null
        set(value) {
            field = value
            getDetails()
        }

    fun manage(name: String, xOffset: Offset, yOffset: Offset, diameter: Float, depth: Float) {
        RelativeDrilling(name = name, xOffset = xOffset, yOffset = yOffset, diameter = diameter, depth = depth).also { drilling ->
            if (relativeDrillingId == null) add(drilling) else update(drilling)
        }
    }

    fun delete() {
        relativeDrillingId?.also {
            relativeDrillingRepository.delete(it)
                .fetchStateFullModel(
                    onLoading = { createLoadingState() },
                    onSuccess = { createResultState() },
                    onError = { createErrorState(it) }
                )
        }
    }

    private fun add(drilling: RelativeDrilling) {
        relativeDrillingSetId?.also {
            relativeDrillingRepository.add(it, drilling)
                .fetchStateFullModel(
                    onLoading = { createLoadingState() },
                    onSuccess = { createResultState() },
                    onError = { createErrorState(it) }
                )
        }
    }

    private fun update(drilling: RelativeDrilling) {
        relativeDrillingId?.also {
            relativeDrillingRepository.update(it, drilling)
                .fetchStateFullModel(
                    onLoading = { createLoadingState() },
                    onSuccess = { createResultState() },
                    onError = { createErrorState(it) }
                )
        }
    }

    private fun getDetails() {
        relativeDrillingId?.also {
            relativeDrillingRepository.get(it)
                .fetchStateFullModel(
                    onLoading = {
                        createLoadingState()
                    },
                    onSuccess = {
                        viewState.updateValue(
                            ManageRelativeDrillingViewState(
                                viewEntity = it.toViewEntity(),
                                manageText = R.string.l_save,
                                isDeleteButtonVisible = true
                            )
                        )
                    },
                    onError = {
                        createErrorState(it)
                    }
                )
        }
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

    private fun RelativeDrilling.toViewEntity(): RelativeDrillingViewEntity {
        return RelativeDrillingViewEntity(
            name = name,
            xOffset = xOffset,
            yOffset = yOffset,
            diameter = measureFormatter.format(diameter),
            depth = measureFormatter.format(depth)
        )
    }
}

class ManageRelativeDrillingViewState(
    val isLoading: Boolean = false,
    val isDeleteButtonVisible: Boolean = false,
    val errorMessage: String? = null,
    val result: Result? = null,
    val viewEntity: RelativeDrillingViewEntity? = null,
    val manageText: Int = R.string.l_add
)

class RelativeDrillingViewEntity(
    val name: String,
    val xOffset: Offset,
    val yOffset: Offset,
    val diameter: String,
    val depth: String
)