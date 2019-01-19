package com.wardrobes.porenut.ui.drilling.relative.manage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.R
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.drilling.relative.RelativeDrillingRepository
import com.wardrobes.porenut.data.drilling.relative.RelativeDrillingRestRepository
import com.wardrobes.porenut.domain.Offset
import com.wardrobes.porenut.domain.RelativeDrilling
import com.wardrobes.porenut.ui.common.DefaultMeasureFormatter
import com.wardrobes.porenut.ui.common.Event
import com.wardrobes.porenut.ui.common.MeasureFormatter
import com.wardrobes.porenut.ui.common.extension.updateValue

class ManageRelativeDrillingViewModel(
    private val relativeDrillingRepository: RelativeDrillingRepository = RelativeDrillingRestRepository,
    private val measureFormatter: MeasureFormatter = DefaultMeasureFormatter
) : ViewModel() {
    val viewState: LiveData<ManageRelativeDrillingViewState> =
        MutableLiveData<ManageRelativeDrillingViewState>().apply {
            value = ManageRelativeDrillingViewState()
        }
    val errorMessageEvent: LiveData<Event<String>> = MutableLiveData()
    val navigateBackEvent: LiveData<Event<Unit>> = MutableLiveData()

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
        relativeDrillingId?.also { id ->
            relativeDrillingRepository.delete(id)
                .fetchStateFullModel(
                    onLoading = { createLoadingState() },
                    onSuccess = { navigateBack() },
                    onError = { createErrorState(it) }
                )
        }
    }

    private fun add(drilling: RelativeDrilling) {
        relativeDrillingSetId?.also { id ->
            relativeDrillingRepository.add(id, drilling)
                .fetchStateFullModel(
                    onLoading = { createLoadingState() },
                    onSuccess = { navigateBack() },
                    onError = { createErrorState(it) }
                )
        }
    }

    private fun update(drilling: RelativeDrilling) {
        relativeDrillingId?.also { id ->
            relativeDrillingRepository.update(id, drilling)
                .fetchStateFullModel(
                    onLoading = { createLoadingState() },
                    onSuccess = { navigateBack() },
                    onError = { createErrorState(it) }
                )
        }
    }

    private fun getDetails() {
        relativeDrillingId?.also { id ->
            relativeDrillingRepository.get(id)
                .fetchStateFullModel(
                    onLoading = { createLoadingState() },
                    onSuccess = {
                        viewState.updateValue(
                            ManageRelativeDrillingViewState(
                                viewEntity = it.toViewEntity(),
                                manageText = R.string.l_save,
                                isDeleteButtonVisible = true
                            )
                        )
                    },
                    onError = { createErrorState(it, shouldNavigateBack = true) }
                )
        }
    }

    private fun createLoadingState() {
        viewState.updateValue(ManageRelativeDrillingViewState(isLoading = true))
    }

    private fun createErrorState(errorMessage: String, shouldNavigateBack: Boolean = false) {
        errorMessageEvent.updateValue(Event(errorMessage))
        if (shouldNavigateBack) navigateBack() else getDetails()
    }

    private fun navigateBack() {
        navigateBackEvent.updateValue(Event(Unit))
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
    val viewEntity: RelativeDrillingViewEntity = RelativeDrillingViewEntity(),
    val manageText: Int = R.string.l_add
)

class RelativeDrillingViewEntity(
    val name: String = "",
    val xOffset: Offset = Offset(),
    val yOffset: Offset = Offset(),
    val diameter: String = "",
    val depth: String = ""
)