package com.wardrobes.porenut.ui.element.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.element.ElementRepository
import com.wardrobes.porenut.data.element.ElementRestRepository
import com.wardrobes.porenut.domain.Element
import com.wardrobes.porenut.ui.common.DefaultMeasureFormatter
import com.wardrobes.porenut.ui.common.Event
import com.wardrobes.porenut.ui.common.MeasureFormatter
import com.wardrobes.porenut.ui.element.detail.ElementViewEntity
import com.wardrobes.porenut.ui.common.extension.updateValue

class ElementGroupViewModel(
    private val elementRepository: ElementRepository = ElementRestRepository,
    private val measureFormatter: MeasureFormatter = DefaultMeasureFormatter
) : ViewModel() {
    val viewState: LiveData<ElementGroupViewState> = MutableLiveData()
    val messageEvent: LiveData<Event<String>> = MutableLiveData()
    val navigateUpEvent: LiveData<Event<Unit>> = MutableLiveData()
    val navigateToDetailsEvent: LiveData<Event<Long>> = MutableLiveData()

    var wardrobeId: String? = null
        set(value) {
            field = value?.also { showElementGroup(value) }
        }

    private var elementGroup: List<Element> = emptyList()

    fun goToDetails(viewEntity: ElementViewEntity) {
        elementGroup.firstOrNull { it.name == viewEntity.name }?.id?.also {
            navigateToDetailsEvent.updateValue(Event(it))
        }
    }

    private fun showElementGroup(wardrobeId: String) {
        elementRepository.getAll(wardrobeId)
            .fetchStateFullModel(
                onLoading = { viewState.updateValue(ElementGroupViewState(isLoading = true)) },
                onSuccess = { elements ->
                    elementGroup = elements
                    viewState.updateValue(
                        ElementGroupViewState(
                            isEmptyListNotificationVisible = elements.isEmpty(),
                            viewEntities = elements.map { it.toViewEntity() }
                        )
                    )
                },
                onError = {
                    messageEvent.updateValue(Event(it))
                    navigateUpEvent.updateValue(Event(Unit))
                }
            )
    }

    private fun Element.toViewEntity() = ElementViewEntity(
        name = name,
        length = length.format(),
        width = width.format(),
        height = height.format()
    )

    private fun Float.format() = measureFormatter.format(this)
}

class ElementGroupViewState(
    val isLoading: Boolean = false,
    val viewEntities: List<ElementViewEntity> = emptyList(),
    val isEmptyListNotificationVisible: Boolean = false
)
