package com.wardrobes.porenut.ui.element.composition.manage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.composition.CompositionRepository
import com.wardrobes.porenut.data.composition.CompositionRestRepository
import com.wardrobes.porenut.data.element.ElementRepository
import com.wardrobes.porenut.data.element.ElementRestRepository
import com.wardrobes.porenut.data.relative.RelativeDrillingCompositionRepository
import com.wardrobes.porenut.data.relative.RelativeDrillingCompositionRestRepository
import com.wardrobes.porenut.domain.Element
import com.wardrobes.porenut.domain.Offset
import com.wardrobes.porenut.domain.ReferenceElementRelativeDrillingCompositionLight
import com.wardrobes.porenut.domain.RelativeDrillingComposition
import com.wardrobes.porenut.ui.vo.Result
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
        xOffset: Offset,
        yOffset: Offset
    ) {
        ReferenceElementRelativeDrillingCompositionLight(
            relativeDrillingCompositionId = drillingCompositions.first { it.name == drillingCompositionName }.id,
            elementId = elementId,
            xOffset = xOffset,
            yOffset = yOffset
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
}

data class ManageCompositionViewState(
    val isLoading: Boolean = false,
    val elementNames: List<String> = emptyList(),
    val compositionNames: List<String> = emptyList(),
    val result: Result? = null,
    val errorMessage: String? = null
)