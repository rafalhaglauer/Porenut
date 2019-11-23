package com.wardrobes.porenut.ui.pattern.drilling.manage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.R
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.pattern.drilling.DrillingPatternRepository
import com.wardrobes.porenut.data.pattern.drilling.DrillingPatternRestRepository
import com.wardrobes.porenut.domain.DrillingDestination
import com.wardrobes.porenut.domain.DrillingPattern
import com.wardrobes.porenut.domain.PositionPattern
import com.wardrobes.porenut.ui.common.Event
import com.wardrobes.porenut.ui.common.extension.updateValue

class ManageDrillingPatternViewModel(
    private val drillingPatternRepository: DrillingPatternRepository = DrillingPatternRestRepository
) : ViewModel() {

    val viewState: LiveData<ManageDrillingPatternViewState> = MutableLiveData()
    val navigateBack: LiveData<Event<Unit>> = MutableLiveData()

    var patternId: String = ""
        set(value) {
            field = value.also { fetchPatternDetails(it) }
        }
    var elementPatternId: String = ""

    var drilling: DrillingPattern? = null

    fun manageElement(viewEntity: ManageDrillingPatternViewEntity) {
        val pattern = createPattern(viewEntity)
        if (patternId.isEmpty()) addPattern(pattern) else updatePattern(pattern)
    }

    fun deletePattern() {
        drillingPatternRepository.delete(patternId)
            .fetchStateFullModel(
                onLoading = { createLoadingState() },
                onSuccess = { navigateBack() },
                onError = { createErrorState(it) }
            )
    }

    private fun fetchPatternDetails(patternId: String) {
        if (patternId.isEmpty()) {
            viewState.updateValue(ManageDrillingPatternViewState())
        } else {
            drillingPatternRepository.get(patternId)
                .fetchStateFullModel(
                    onLoading = { createLoadingState() },
                    onSuccess = { createDetailsState(it) },
                    onError = { createErrorState(it) }
                )
        }
    }

    private fun addPattern(pattern: DrillingPattern) {
        drillingPatternRepository.add(elementPatternId, pattern)
            .fetchStateFullModel(
                onLoading = { createLoadingState() },
                onSuccess = { navigateBack() },
                onError = { createErrorState(it) }
            )
    }

    private fun updatePattern(pattern: DrillingPattern) {
        drillingPatternRepository.update(pattern)
            .fetchStateFullModel(
                onLoading = { createLoadingState() },
                onSuccess = { navigateBack() },
                onError = { createErrorState(it) }
            )
    }

    private fun createLoadingState() {
        viewState.updateValue(ManageDrillingPatternViewState(isLoading = true))
    }

    private fun createDetailsState(drilling: DrillingPattern) {
        this.drilling = drilling
        viewState.updateValue(ManageDrillingPatternViewState(viewEntity = map(drilling), btnActionText = R.string.l_save))
    }

    private fun createErrorState(errorMessage: String) {
        viewState.updateValue(ManageDrillingPatternViewState(errorMessage = errorMessage))
    }

    private fun navigateBack() {
        navigateBack.updateValue(Event(Unit))
    }

    private fun map(pattern: DrillingPattern) = ManageDrillingPatternViewEntity(
        name = pattern.name,
        positionX = pattern.positionX.pattern,
        positionY = pattern.positionY.pattern
    )

    private fun createPattern(viewEntity: ManageDrillingPatternViewEntity): DrillingPattern {
        return DrillingPattern(
            id = patternId,
            name = viewEntity.name,
            positionX = PositionPattern(id = drilling?.positionX?.id.orEmpty(), pattern = viewEntity.positionX),
            positionY = PositionPattern(id = drilling?.positionY?.id.orEmpty(), pattern = viewEntity.positionY),
            destination = DrillingDestination.UNKNOWN // TODO!
        )
    }

}

class ManageDrillingPatternViewState(
    val isLoading: Boolean = false,
    val viewEntity: ManageDrillingPatternViewEntity = ManageDrillingPatternViewEntity(),
    val errorMessage: String = "",
    val btnActionText: Int = R.string.l_add
)

data class ManageDrillingPatternViewEntity(
    val name: String = "",
    val positionX: String = "",
    val positionY: String = ""
)