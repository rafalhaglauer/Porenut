package com.wardrobes.porenut.ui.element

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.element.ElementRepository
import com.wardrobes.porenut.data.element.ElementRestRepository
import com.wardrobes.porenut.domain.Element
import com.wardrobes.porenut.domain.UNDEFINED_ID
import com.wardrobes.porenut.domain.Wardrobe
import com.wardrobes.porenut.ui.vo.DefaultMeasureFormatter
import com.wardrobes.porenut.ui.vo.MeasureFormatter
import com.wardrobes.porenut.ui.wardrobe.detail.ElementViewEntity

class ElementDetailsViewModel(
    private val elementRepository: ElementRepository = ElementRestRepository,
    private val measureFormatter: MeasureFormatter = DefaultMeasureFormatter
) : ViewModel() {
    val viewState: LiveData<ElementViewState> by lazy {
        MutableLiveData<ElementViewState>()
    }

    var creationType: Wardrobe.CreationType? = null

    var elementId: Long = UNDEFINED_ID
        set(value) {
            field = value
            fetchDetails()
        }

    fun delete() {
        elementRepository.delete(elementId)
            .fetchStateFullModel(
                onLoading = { createLoadingState() },
                onSuccess = { createDeletedState() },
                onError = { createErrorState(it) }
            )
    }

    private fun fetchDetails() {
        elementRepository.get(elementId)
            .fetchStateFullModel(
                onLoading = { createLoadingState() },
                onSuccess = { createSuccessState(it) },
                onError = { createErrorState(it) }
            )
    }

    private fun createLoadingState() {
        viewState.update(ElementViewState(isLoading = true))
    }

    private fun createSuccessState(element: Element) {
        viewState.update(
            ElementViewState(
                viewEntity = element.toViewEntity(),
                isManageButtonVisible = creationType == Wardrobe.CreationType.CUSTOM
            )
        )
    }

    private fun createErrorState(errorMessage: String?) {
        viewState.update(ElementViewState(errorMessage = errorMessage))
    }

    private fun createDeletedState() {
        viewState.update(ElementViewState(isDeleted = true))
    }

    private fun LiveData<ElementViewState>.update(viewState: ElementViewState) {
        (this as MutableLiveData).value = viewState
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

class ElementViewState(
    val isLoading: Boolean = false,
    val isDeleted: Boolean = false,
    val isManageButtonVisible: Boolean = false,
    val viewEntity: ElementViewEntity? = null,
    val errorMessage: String? = null
)