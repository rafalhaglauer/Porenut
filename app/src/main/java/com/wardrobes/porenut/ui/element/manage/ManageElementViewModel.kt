package com.wardrobes.porenut.ui.element.manage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.R
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.element.ElementRepository
import com.wardrobes.porenut.data.element.ElementRestRepository
import com.wardrobes.porenut.domain.Element
import com.wardrobes.porenut.ui.common.DefaultMeasureFormatter
import com.wardrobes.porenut.ui.common.Event
import com.wardrobes.porenut.ui.common.MeasureFormatter
import com.wardrobes.porenut.ui.element.detail.ElementViewEntity
import com.wardrobes.porenut.ui.common.extension.updateValue

class ManageElementViewModel(
    private val elementRepository: ElementRepository = ElementRestRepository,
    private val measureFormatter: MeasureFormatter = DefaultMeasureFormatter
) : ViewModel() {
    val viewState: LiveData<ManageElementViewState> = MutableLiveData<ManageElementViewState>().apply {
        value = ManageElementViewState()
    }
    val errorStateEvent: LiveData<Event<String>> = MutableLiveData()
    val navigateBackEvent: LiveData<Event<Unit>> = MutableLiveData()

    var wardrobeId: String? = null
    var elementId: Long? = null
        set(value) {
            field = value?.also { fetchDetails(it) }
        }

    fun manage(elementViewEntity: ElementViewEntity) {
        val element = elementViewEntity.toElement()
        elementId
            ?.also { update(element, it) }
            ?: add(element)
    }

    private fun fetchDetails(elementId: Long) {
        elementRepository.get(elementId)
            .fetchStateFullModel(
                onLoading = { setLoading() },
                onSuccess = { createInitialState(it) },
                onError = { createErrorState(it, shouldNavigateBack = true) }
            )
    }

    private fun add(element: Element) {
        wardrobeId?.also { wardrobeId ->
            elementRepository.add(wardrobeId, element)
                .fetchStateFullModel(
                    onLoading = { setLoading() },
                    onSuccess = { navigateBack() },
                    onError = { createErrorState(it, shouldNavigateBack = false) }
                )
        }
    }

    private fun update(element: Element, elementId: Long) {
        elementRepository.update(elementId, element)
            .fetchStateFullModel(
                onLoading = { setLoading() },
                onSuccess = { navigateBack() },
                onError = { createErrorState(it, shouldNavigateBack = false) }
            )
    }

    private fun setLoading(isLoading: Boolean = true) {
        viewState.updateValue(ManageElementViewState(isLoading = isLoading))
    }

    private fun createInitialState(element: Element) {
        viewState.updateValue(ManageElementViewState(viewEntity = element.toViewEntity(), btnTextMessage = R.string.l_save))
    }

    private fun createErrorState(errorMessage: String, shouldNavigateBack: Boolean) {
        errorStateEvent.updateValue(Event(errorMessage))
        if (shouldNavigateBack) navigateBack() else setLoading(false)
    }

    private fun navigateBack() {
        navigateBackEvent.updateValue(Event(Unit))
    }

    private fun Element.toViewEntity() = ElementViewEntity(
        name = name,
        length = length.formattedValue,
        width = width.formattedValue,
        height = height.formattedValue
    )

    private fun ElementViewEntity.toElement() = Element(
        name = name,
        length = length.toFloat(),
        width = width.toFloat(),
        height = height.toFloat()
    )

    private fun String.toFloat() = measureFormatter.toFloat(this)

    private val Float.formattedValue: String
        get() = measureFormatter.format(this)
}

class ManageElementViewState(
    val isLoading: Boolean = false,
    val viewEntity: ElementViewEntity = ElementViewEntity(),
    val btnTextMessage: Int = R.string.l_add
)