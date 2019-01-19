package com.wardrobes.porenut.ui.element.composition.manage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.R
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.composition.ElementDrillingSetCompositionRepository
import com.wardrobes.porenut.data.composition.ElementDrillingSetCompositionRequest
import com.wardrobes.porenut.data.composition.ElementDrillingSetCompositionRestRepository
import com.wardrobes.porenut.data.drilling.set.RelativeDrillingSetRepository
import com.wardrobes.porenut.data.drilling.set.RelativeDrillingSetRestRepository
import com.wardrobes.porenut.domain.ElementDrillingSetComposition
import com.wardrobes.porenut.domain.Offset
import com.wardrobes.porenut.domain.RelativeDrillingSet
import com.wardrobes.porenut.ui.common.Event
import com.wardrobes.porenut.ui.common.extension.updateValue

class ManageElementCompositionViewModel(
    private val relativeDrillingCompositionRepository: RelativeDrillingSetRepository = RelativeDrillingSetRestRepository,
    private val compositionRepository: ElementDrillingSetCompositionRepository = ElementDrillingSetCompositionRestRepository
) : ViewModel() {
    val viewState: LiveData<ManageElementCompositionViewState> = MutableLiveData()
    val errorMessageEvent: LiveData<Event<String>> = MutableLiveData()
    val navigateBackEvent: LiveData<Event<Unit>> = MutableLiveData()

    var compositionId: Long? = null
        set(value) {
            field = value?.also { fetchComposition(it) }
        }
    var elementId: Long? = null
        set(value) {
            field = value?.also { fetchInitialData() }
        }

    private var drillingSets: List<RelativeDrillingSet> = emptyList()
    private var composition: ElementDrillingSetComposition? = null
        set(value) {
            field = value?.also { elementId = it.element.id }
        }

    fun manage(drillingCompositionName: String, xOffset: Offset, yOffset: Offset) {
        elementId?.also { manage(it, drillingCompositionName, xOffset, yOffset) }
    }

    fun remove() {
        compositionId?.also { id ->
            compositionRepository.delete(id).fetchStateFullModel(
                onLoading = { createLoadingState() },
                onSuccess = { createSuccessState() },
                onError = { createErrorState(it) }
            )

        }
    }

    private fun manage(elementId: Long, drillingCompositionName: String, xOffset: Offset, yOffset: Offset) {
        drillingSets.firstOrNull { it.name == drillingCompositionName }?.id?.also { drillingSetId ->
            ElementDrillingSetCompositionRequest(
                drillingSetId = drillingSetId,
                elementId = elementId,
                xOffset = xOffset,
                yOffset = yOffset
            ).also { request ->
                compositionId
                    ?.also { update(it, request) }
                    ?: add(request)
            }
        }
    }

    private fun add(composition: ElementDrillingSetCompositionRequest) {
        compositionRepository.add(composition)
            .fetchStateFullModel(
                onLoading = { createLoadingState() },
                onSuccess = { createSuccessState() },
                onError = { createErrorState(it) }
            )
    }

    private fun update(compositionId: Long, composition: ElementDrillingSetCompositionRequest) {
        compositionRepository.update(compositionId, composition)
            .fetchStateFullModel(
                onLoading = { createLoadingState() },
                onSuccess = { createSuccessState() },
                onError = { createErrorState(it) }
            )
    }

    private fun fetchInitialData() {
        relativeDrillingCompositionRepository.getAll().fetchStateFullModel(
            onLoading = { createLoadingState() },
            onSuccess = {
                drillingSets = it
                createInitialState(it)
            },
            onError = { createErrorState(it) }
        )
    }

    private fun fetchComposition(compositionId: Long) {
        compositionRepository.get(compositionId).fetchStateFullModel(
            onLoading = { createLoadingState() },
            onSuccess = { composition = it },
            onError = { createErrorState(it) }
        )
    }

    private fun createLoadingState() {
        viewState.updateValue(ManageElementCompositionViewState(isLoading = true))
    }

    private fun createInitialState(drillingSets: List<RelativeDrillingSet>) {
        val viewEntity = composition?.toViewEntity() ?: ManageElementCompositionViewEntity()
        viewState.updateValue(
            ManageElementCompositionViewState(
                compositionNames = drillingSets.map { it.name },
                isEditMode = composition != null,
                viewEntity = viewEntity
            )
        )
    }

    private fun createSuccessState() {
        navigateBack()
    }

    private fun createErrorState(errorMessage: String, shouldNavigateBack: Boolean = false) {
        errorMessageEvent.updateValue(Event(errorMessage))
        if (shouldNavigateBack) navigateBack() else createInitialState(drillingSets)
    }

    private fun navigateBack() {
        navigateBackEvent.updateValue(Event(Unit))
    }

    private fun ElementDrillingSetComposition.toViewEntity() = ManageElementCompositionViewEntity(
        drillingSetPosition = drillingSets.indexOf(drillingSet),
        xOffset = xOffset,
        yOffset = yOffset
    )
}

class ManageElementCompositionViewState(
    val isLoading: Boolean = false,
    val compositionNames: List<String> = emptyList(),
    val viewEntity: ManageElementCompositionViewEntity = ManageElementCompositionViewEntity(),
    isEditMode: Boolean = false
) {
    val isRemovePossible: Boolean = isEditMode
    val isDrillingSetPositionEnable: Boolean = !isEditMode
    val buttonText: Int = if (isEditMode) R.string.l_save else R.string.l_add
}

data class ManageElementCompositionViewEntity(
    val drillingSetPosition: Int = 0,
    val xOffset: Offset = Offset(),
    val yOffset: Offset = Offset()
)