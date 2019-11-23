package com.wardrobes.porenut.ui.pattern.element.manage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.R
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.pattern.element.ElementPatternRepository
import com.wardrobes.porenut.data.pattern.element.ElementPatternRestRepository
import com.wardrobes.porenut.domain.ElementPattern
import com.wardrobes.porenut.domain.LengthPattern
import com.wardrobes.porenut.ui.common.Event
import com.wardrobes.porenut.ui.common.extension.updateValue

class ManageElementPatternViewModel(
    private val elementPatternRepository: ElementPatternRepository = ElementPatternRestRepository
) : ViewModel() {

    val viewState: LiveData<ManageElementPatternViewState> = MutableLiveData()
    val navigateBack: LiveData<Event<Unit>> = MutableLiveData()

    var patternId: String = ""
        set(value) {
            field = value.also { fetchPatternDetails(it) }
        }
    var wardrobePatternId: String = ""

    var element: ElementPattern? = null

    fun manageElement(viewEntity: ManageElementPatternViewEntity) {
        val pattern = createPattern(viewEntity)
        if (patternId.isEmpty()) addPattern(pattern) else updatePattern(pattern)
    }

    fun deletePattern() {
        elementPatternRepository.delete(patternId)
            .fetchStateFullModel(
                onLoading = { createLoadingState() },
                onSuccess = { navigateBack() },
                onError = { createErrorState(it) }
            )
    }

    private fun fetchPatternDetails(patternId: String) {
        if (patternId.isEmpty()) {
            viewState.updateValue(ManageElementPatternViewState())
        } else {
            elementPatternRepository.get(patternId)
                .fetchStateFullModel(
                    onLoading = { createLoadingState() },
                    onSuccess = { createDetailsState(it) },
                    onError = { createErrorState(it) }
                )
        }
    }

    private fun addPattern(pattern: ElementPattern) {
        elementPatternRepository.add(wardrobePatternId, pattern)
            .fetchStateFullModel(
                onLoading = { createLoadingState() },
                onSuccess = { navigateBack() },
                onError = { createErrorState(it) }
            )
    }

    private fun updatePattern(pattern: ElementPattern) {
        elementPatternRepository.update(pattern)
            .fetchStateFullModel(
                onLoading = { createLoadingState() },
                onSuccess = { navigateBack() },
                onError = { createErrorState(it) }
            )
    }

    private fun createLoadingState() {
        viewState.updateValue(ManageElementPatternViewState(isLoading = true))
    }

    private fun createDetailsState(element: ElementPattern) {
        this.element = element
        viewState.updateValue(ManageElementPatternViewState(viewEntity = map(element), btnActionText = R.string.l_save))
    }

    private fun createErrorState(errorMessage: String) {
        viewState.updateValue(ManageElementPatternViewState(errorMessage = errorMessage))
    }

    private fun navigateBack() {
        navigateBack.updateValue(Event(Unit))
    }

    private fun map(pattern: ElementPattern) = ManageElementPatternViewEntity(
        name = pattern.name,
        lengthPattern = pattern.length.pattern,
        widthPattern = pattern.width.pattern,
        height = pattern.height.toString()
    )

    private fun createPattern(viewEntity: ManageElementPatternViewEntity): ElementPattern {
        return ElementPattern(
            id = patternId,
            name = viewEntity.name,
            length = LengthPattern(id = element?.length?.id.orEmpty(), pattern = viewEntity.lengthPattern),
            width = LengthPattern(id = element?.width?.id.orEmpty(), pattern = viewEntity.widthPattern),
            height = viewEntity.height.toIntOrNull() ?: 0
        )
    }

}

class ManageElementPatternViewState(
    val isLoading: Boolean = false,
    val viewEntity: ManageElementPatternViewEntity = ManageElementPatternViewEntity(),
    val errorMessage: String = "",
    val btnActionText: Int = R.string.l_add
)

data class ManageElementPatternViewEntity(
    val name: String = "",
    val lengthPattern: String = "",
    val widthPattern: String = "",
    val height: String = ""
)