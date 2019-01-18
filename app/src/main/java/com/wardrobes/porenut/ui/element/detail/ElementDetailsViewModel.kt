package com.wardrobes.porenut.ui.element.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.element.ElementRepository
import com.wardrobes.porenut.data.element.ElementRestRepository
import com.wardrobes.porenut.domain.Element
import com.wardrobes.porenut.ui.extension.updateValue
import com.wardrobes.porenut.ui.vo.DefaultMeasureFormatter
import com.wardrobes.porenut.ui.vo.Event
import com.wardrobes.porenut.ui.vo.MeasureFormatter

class ElementDetailsViewModel(
    private val elementRepository: ElementRepository = ElementRestRepository,
    private val measureFormatter: MeasureFormatter = DefaultMeasureFormatter
) : ViewModel() {
    val viewState: LiveData<ElementDetailViewState> = MutableLiveData()
    val errorEvent: LiveData<Event<String>> = MutableLiveData()

    var elementId: Long? = null
        set(value) {
            field = value?.also { fetchDetails(it) }
        }

    private fun fetchDetails(elementId: Long) {
        elementRepository.get(elementId)
            .fetchStateFullModel(
                onLoading = { viewState.updateValue(ElementDetailViewState(isLoading = true)) },
                onSuccess = { viewState.updateValue(ElementDetailViewState(viewEntity = it.toViewEntity())) },
                onError = { errorEvent.updateValue(Event(it)) }
            )
    }

    private fun Element.toViewEntity() = ElementViewEntity(
        name = name,
        length = length.formattedValue,
        width = width.formattedValue,
        height = height.formattedValue
    )

    private val Float.formattedValue: String
        get() = measureFormatter.format(this)
}

data class ElementDetailViewState(
    val isLoading: Boolean = false,
    val viewEntity: ElementViewEntity = ElementViewEntity(),
    val isActionButtonVisible: Boolean = false
)

data class ElementViewEntity(
    val name: String = "",
    val length: String = "",
    val width: String = "",
    val height: String = ""
)