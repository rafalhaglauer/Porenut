package com.wardrobes.porenut.ui.v2.element

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.api.extension.fetchStateFullModel
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
    val measureFormatter: MeasureFormatter = DefaultMeasureFormatter
) : ViewModel() {
    val viewState: LiveData<ElementTabViewState> = MutableLiveData<ElementTabViewState>().apply {
        value = ElementTabViewState(areDetailsVisible = true)
    }
    val elementDetailViewState: LiveData<ElementDetailViewState> = MutableLiveData()
    val drillingGroupViewState: LiveData<DrillingGroupViewState> = MutableLiveData()
    val messageEvent: LiveData<Event<String>> = MutableLiveData()
    val navigateUpEvent: LiveData<Event<Unit>> = MutableLiveData()

    var elementId: Long? = null

    fun showDetails() {
        viewState.updateValue(ElementTabViewState(areDetailsVisible = true))
        elementId?.also { elementId ->
            elementRepository.get(elementId)
                .fetchStateFullModel(
                    onLoading = {
                        elementDetailViewState.updateValue(ElementDetailViewState(isLoading = true))
                    },
                    onSuccess = {
                        val isActionButtonVisible = it.creationType == Element.CreationType.CUSTOM
                        elementDetailViewState.updateValue(
                            ElementDetailViewState(viewEntity = it.toViewEntity(), isActionButtonVisible = isActionButtonVisible)
                        )
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

    fun showRelativeDrillingGroup() {
        viewState.updateValue(ElementTabViewState(isRelativeDrillingGroupVisible = true))
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

    private fun List<Drilling>.toViewEntities() = map { it.toViewEntity() }

    private fun Element.toViewEntity() = ElementViewEntity(
        name = name,
        length = length.formattedValue,
        width = width.formattedValue,
        height = height.formattedValue
    )

    private fun Drilling.toViewEntity() = DrillingViewEntity(
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
    val isRelativeDrillingGroupVisible: Boolean = false
)

data class ElementDetailViewState(
    val isLoading: Boolean = false,
    val viewEntity: ElementViewEntity? = null,
    val isActionButtonVisible: Boolean = false
)

class DrillingGroupViewState(
    val isLoading: Boolean = false,
    val viewEntities: List<DrillingViewEntity> = emptyList(),
    val isEmptyListNotificationVisible: Boolean = false
)

data class DrillingViewEntity(
    val xPosition: String,
    val yPosition: String,
    val diameter: String,
    val depth: String,
    val isBlocked: Boolean
)

data class ElementViewEntity(
    val name: String,
    val length: String,
    val width: String,
    val height: String
)