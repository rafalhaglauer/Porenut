package com.wardrobes.porenut.ui.element.composition

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.composition.CompositionRepository
import com.wardrobes.porenut.data.composition.CompositionRestRepository
import com.wardrobes.porenut.data.element.ElementRepository
import com.wardrobes.porenut.data.element.ElementRestRepository
import com.wardrobes.porenut.data.relative.RelativeDrillingCompositionRepository
import com.wardrobes.porenut.data.relative.RelativeDrillingCompositionRestRepository
import com.wardrobes.porenut.domain.CompositeOffset
import com.wardrobes.porenut.domain.Element
import com.wardrobes.porenut.domain.ReferenceElementRelativeDrillingCompositionLight
import com.wardrobes.porenut.domain.RelativeDrillingComposition
import com.wardrobes.porenut.ui.wardrobe.Result
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class ManageCompositionViewModel(
    private val elementRepository: ElementRepository = ElementRestRepository,
    private val relativeDrillingCompositionRepository: RelativeDrillingCompositionRepository = RelativeDrillingCompositionRestRepository,
    private val compositionRepository: CompositionRepository = CompositionRestRepository
) : ViewModel() {
    val viewState: MutableLiveData<ManageCompositionViewState> = MutableLiveData()

    var elementId: Long = -1

    var wardrobeId: Long = -1
        set(value) {
            field = value
            fetchInitialData()
        }

    private var elements: List<Element> = emptyList()
    private var drillingCompositions: List<RelativeDrillingComposition> = emptyList()

    fun add(
        drillingCompositionName: String,
        referenceElementName: String,
        xOffset: CompositeOffset,
        yOffset: CompositeOffset,
        xReferenceLengthName: String,
        yReferenceLengthName: String
    ) {
        ReferenceElementRelativeDrillingCompositionLight(
            relativeDrillingCompositionId = drillingCompositions.first { it.name == drillingCompositionName }.id,
            referenceElementId = elements.first { it.name == referenceElementName }.id,
            elementId = elementId,
            xOffset = xOffset,
            yOffset = yOffset,
            xReferenceLength = xReferenceLengthName.toElementLengthType(),
            yReferenceLength = yReferenceLengthName.toElementLengthType()
        ).also { add(it) }
    }

    private fun add(composition: ReferenceElementRelativeDrillingCompositionLight) {
        compositionRepository.add(composition)
            .fetchStateFullModel(
                onLoading = { createLoadingState() },
                onSuccess = { createResultState() },
                onError = { createErrorState(it) }
            )
    }

    private fun fetchInitialData() {
        Observable.zip(
            elementRepository.getAll(wardrobeId),
            relativeDrillingCompositionRepository.getAll(),
            BiFunction<List<Element>, List<RelativeDrillingComposition>, Pair<List<Element>, List<RelativeDrillingComposition>>>(
                function = { elements, compositions -> elements to compositions })
        ).fetchStateFullModel(
            onLoading = { createLoadingState() },
            onSuccess = {
                elements = it.first
                drillingCompositions = it.second
                createInitialState()
            },
            onError = { createErrorState(it) }
        )
    }

    private fun createLoadingState() {
        viewState.value = ManageCompositionViewState(isLoading = true)
    }

    private fun createInitialState() {
        viewState.value = ManageCompositionViewState(
            elementNames = elements.map { it.name },
            compositionNames = drillingCompositions.map { it.name }
        )
    }

    private fun createResultState() {
        viewState.value = viewState.value?.copy(result = Result.ADDED)
    }

    private fun createErrorState(errorMessage: String?) {
        viewState.value = ManageCompositionViewState(errorMessage = errorMessage)
    }

    private fun String.toElementLengthType(): Element.LengthType {
        return when (this) {
            "Długość elementu" -> Element.LengthType.LENGTH
            "Szerokość elementu" -> Element.LengthType.WIDTH
            else -> Element.LengthType.HEIGHT
        }
    }
}

data class ManageCompositionViewState(
    val isLoading: Boolean = false,
    val elementNames: List<String> = emptyList(),
    val compositionNames: List<String> = emptyList(),
    val result: Result? = null,
    val errorMessage: String? = null
)