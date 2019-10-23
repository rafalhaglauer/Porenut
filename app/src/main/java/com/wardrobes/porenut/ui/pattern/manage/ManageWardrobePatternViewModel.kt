package com.wardrobes.porenut.ui.pattern.manage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.R
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.pattern.WardrobePatternRepository
import com.wardrobes.porenut.data.pattern.WardrobePatternRestRepository
import com.wardrobes.porenut.domain.WardrobePattern
import com.wardrobes.porenut.ui.common.Event
import com.wardrobes.porenut.ui.common.extension.updateValue

class ManageWardrobePatternViewModel(
    private val wardrobePatternRepository: WardrobePatternRepository = WardrobePatternRestRepository
) : ViewModel() {

    val viewState: LiveData<ManageWardrobePatternViewState> = MutableLiveData()
    val navigateBack: LiveData<Event<Unit>> = MutableLiveData()

    var patternId: String = ""
        set(value) {
            field = value.also { fetchPatternDetails(it) }
        }

    fun manageWardrobe(viewEntity: ManageWardrobePatternViewEntity) {
        val pattern = createPattern(viewEntity)
        if (patternId.isEmpty()) addPattern(pattern) else updatePattern(pattern)
    }

    private fun fetchPatternDetails(patternId: String) {
        if (patternId.isEmpty()) {
            viewState.updateValue(ManageWardrobePatternViewState())
        } else {
            wardrobePatternRepository.get(patternId)
                .fetchStateFullModel(
                    onLoading = { createLoadingState() },
                    onSuccess = { createDetailsState(it) },
                    onError = { createErrorState(it) }
                )
        }
    }

    private fun addPattern(pattern: WardrobePattern) {
        wardrobePatternRepository.add(pattern)
            .fetchStateFullModel(
                onLoading = { createLoadingState() },
                onSuccess = { navigateBack() },
                onError = { createErrorState(it) }
            )
    }

    private fun updatePattern(pattern: WardrobePattern) {
        wardrobePatternRepository.update(pattern)
            .fetchStateFullModel(
                onLoading = { createLoadingState() },
                onSuccess = { navigateBack() },
                onError = { createErrorState(it) }
            )
    }

    private fun createLoadingState() {
        viewState.updateValue(ManageWardrobePatternViewState(isLoading = true))
    }

    private fun createDetailsState(wardrobe: WardrobePattern) {
        viewState.updateValue(ManageWardrobePatternViewState(viewEntity = map(wardrobe), btnActionText = R.string.l_save))
    }

    private fun createErrorState(errorMessage: String) {
        viewState.updateValue(ManageWardrobePatternViewState(errorMessage = errorMessage))
    }

    private fun navigateBack() {
        navigateBack.updateValue(Event(Unit))
    }

    private fun map(pattern: WardrobePattern) = ManageWardrobePatternViewEntity(symbol = pattern.symbol, description = pattern.description)

    private fun createPattern(viewEntity: ManageWardrobePatternViewEntity): WardrobePattern {
        return WardrobePattern(id = patternId, symbol = viewEntity.symbol, description = viewEntity.description)
    }

}

class ManageWardrobePatternViewState(
    val isLoading: Boolean = false,
    val viewEntity: ManageWardrobePatternViewEntity = ManageWardrobePatternViewEntity(),
    val errorMessage: String = "",
    val btnActionText: Int = R.string.l_add
)

data class ManageWardrobePatternViewEntity(
    val symbol: String = "",
    val description: String = ""
)