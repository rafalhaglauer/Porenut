package com.wardrobes.porenut.ui.element.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.composition.CompositionRepository
import com.wardrobes.porenut.data.composition.CompositionRestRepository
import com.wardrobes.porenut.data.drilling.DrillingRepository
import com.wardrobes.porenut.data.drilling.DrillingRestRepository
import com.wardrobes.porenut.data.element.ElementRepository
import com.wardrobes.porenut.data.element.ElementRestRepository
import com.wardrobes.porenut.domain.Drilling
import com.wardrobes.porenut.domain.Element
import com.wardrobes.porenut.ui.extension.updateValue
import com.wardrobes.porenut.ui.vo.DefaultMeasureFormatter
import com.wardrobes.porenut.ui.vo.Event
import com.wardrobes.porenut.ui.vo.MeasureFormatter

class ElementTabViewModel(
    private val elementRepository: ElementRepository = ElementRestRepository,
    private val drillingRepository: DrillingRepository = DrillingRestRepository,
    private val compositionRepository: CompositionRepository = CompositionRestRepository,
    private val measureFormatter: MeasureFormatter = DefaultMeasureFormatter
) : ViewModel() {
    val viewState: LiveData<ElementTabViewState> = MutableLiveData<ElementTabViewState>().apply {
        value = ElementTabViewState(areDetailsVisible = true)
    }
    val elementDetailViewState: LiveData<ElementDetailViewState> = MutableLiveData()
    val drillingGroupViewState: LiveData<DrillingGroupViewState> = MutableLiveData()
    val compositionGroupViewState: LiveData<ElementCompositionViewState> = MutableLiveData()
    val messageEvent: LiveData<Event<String>> = MutableLiveData()
    val navigateUpEvent: LiveData<Event<Unit>> = MutableLiveData()

    var elementId: Long? = null

    private var drillingGroup: List<Drilling> = emptyList()
    private var element: Element? = null

    fun showDetails() {
        viewState.updateValue(ElementTabViewState(areDetailsVisible = true))
        elementId?.also { elementId ->
            elementRepository.get(elementId)
                .fetchStateFullModel(
                    onLoading = {
                        elementDetailViewState.updateValue(ElementDetailViewState(isLoading = true))
                    },
                    onSuccess = {
                        element = it
                        elementDetailViewState.updateValue(ElementDetailViewState(viewEntity = it.toViewEntity()))
                    },
                    onError = {
                        messageEvent.updateValue(Event(it))
                        navigateUpEvent.updateValue(Event(Unit))
                    }
                )
        }
    }

    fun showDrillingGroup() {
        viewState.updateValue(ElementTabViewState(isDrillingGroupVisible = true))
        elementId?.also { elementId ->
            drillingRepository.getAll(elementId)
                .fetchStateFullModel(
                    onLoading = {
                        drillingGroupViewState.updateValue(DrillingGroupViewState(isLoading = true))
                    },
                    onSuccess = {
                        drillingGroup = it
                        if (it.isEmpty()) {
                            DrillingGroupViewState(isEmptyListNotificationVisible = true)
                        } else {
                            DrillingGroupViewState(viewEntities = it.toViewEntities())
                        }.also { drillingGroupViewState.updateValue(it) }
                    },
                    onError = {
                        messageEvent.updateValue(Event(it))
                        drillingGroupViewState.updateValue(DrillingGroupViewState())
                    }
                )
        }
    }

    fun showCompositionGroup() {
        viewState.updateValue(ElementTabViewState(isCompositionGroupVisible = true))
        elementId?.also { id ->
            compositionRepository.getAll(id)
                .fetchStateFullModel(
                    onLoading = {
                        compositionGroupViewState.updateValue(ElementCompositionViewState(isLoading = true))
                    },
                    onSuccess = {
                        if (it.isEmpty()) {
                            ElementCompositionViewState(isEmptyListNotificationVisible = true)
                        } else {
                            ElementCompositionViewState(names = it.map { it.id.toString() })
                        }.also { state -> compositionGroupViewState.updateValue(state) }
                    },
                    onError = {
                        messageEvent.updateValue(Event(it))
                        compositionGroupViewState.updateValue(ElementCompositionViewState())
                    }
                )
        }
    }

    fun deleteElement() {
        elementId?.also {
            elementRepository.delete(it)
                .fetchStateFullModel(
                    onLoading = {
                        elementDetailViewState.value?.also {
                            elementDetailViewState.updateValue(it.copy(isLoading = true))
                        }
                    },
                    onSuccess = {
                        messageEvent.updateValue(Event("Pomyślnie usunięto element!"))
                        navigateUpEvent.updateValue(Event(Unit))
                    },
                    onError = {
                        messageEvent.updateValue(Event(it))
                        elementDetailViewState.value?.also {
                            elementDetailViewState.updateValue(it.copy(isLoading = false))
                        }
                    }
                )
        }
    }

    fun getDrillingId(viewEntity: DrillingGroupViewEntity): Long = drillingGroup.first { it.toViewEntity() == viewEntity }.id

    private fun List<Drilling>.toViewEntities() = map { it.toViewEntity() }

    private fun Element.toViewEntity() = ElementViewEntity(
        name = name,
        length = length.formattedValue,
        width = width.formattedValue,
        height = height.formattedValue
    )

    private fun Drilling.toViewEntity() = DrillingGroupViewEntity(
        xPosition = xPosition.formattedValue,
        yPosition = yPosition.formattedValue,
        diameter = diameter.formattedValue,
        depth = depth.formattedValue,
        isBlocked = type == Drilling.CreationType.GENERATED
    )

    private val Float.formattedValue: String
        get() = measureFormatter.format(this)
}

class ElementTabViewState(
    val areDetailsVisible: Boolean = false,
    val isDrillingGroupVisible: Boolean = false,
    val isCompositionGroupVisible: Boolean = false
)

data class ElementDetailViewState(
    val isLoading: Boolean = false,
    val viewEntity: ElementViewEntity? = null,
    val isActionButtonVisible: Boolean = false
)

class DrillingGroupViewState(
    val isLoading: Boolean = false,
    val viewEntities: List<DrillingGroupViewEntity> = emptyList(),
    val isEmptyListNotificationVisible: Boolean = false,
    val isActionButtonVisible: Boolean = false
)

data class DrillingGroupViewEntity(
    val xPosition: String,
    val yPosition: String,
    val diameter: String,
    val depth: String,
    val isBlocked: Boolean
)

data class ElementViewEntity(
    val name: String = "",
    val length: String = "",
    val width: String = "",
    val height: String = ""
)

class ElementCompositionViewState(
    val isLoading: Boolean = false,
    val names: List<String> = emptyList(),
    val isEmptyListNotificationVisible: Boolean = false,
    val isActionButtonVisible: Boolean = false
)