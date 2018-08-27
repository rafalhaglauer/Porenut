package com.wardrobes.porenut.ui.element

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.R
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.element.ElementRepository
import com.wardrobes.porenut.data.element.ElementRestRepository
import com.wardrobes.porenut.domain.Element
import com.wardrobes.porenut.domain.ElementLight
import com.wardrobes.porenut.domain.UNDEFINED_ID
import com.wardrobes.porenut.ui.vo.DefaultMeasureFormatter
import com.wardrobes.porenut.ui.vo.MeasureFormatter
import com.wardrobes.porenut.ui.wardrobe.RequestType
import com.wardrobes.porenut.ui.wardrobe.Result
import com.wardrobes.porenut.ui.wardrobe.detail.ElementViewEntity

class ManageElementViewModel(
    private val elementRepository: ElementRepository = ElementRestRepository,
    private val measureFormatter: MeasureFormatter = DefaultMeasureFormatter
) : ViewModel() {
    val viewState: LiveData<ManageElementViewState> = MutableLiveData()

    var wardrobeId = UNDEFINED_ID
    var elementId = UNDEFINED_ID

    var requestType: RequestType? = null
        set(value) {
            field = value
            when (value) {
                RequestType.ADD -> viewState.updateValue(ManageElementViewState(isLoading = false))
                else -> fetchDetails()
            }
        }

    fun manage(elementViewEntity: ElementViewEntity) {
        when (requestType) {
            RequestType.EDIT -> update(elementViewEntity)
            RequestType.COPY -> copy(elementViewEntity)
            RequestType.ADD -> add(elementViewEntity)
        }
    }

    private fun fetchDetails() {
        elementRepository.get(elementId)
            .fetchStateFullModel(
                onLoading = { createLoadingState() },
                onSuccess = { createInitialState(it) },
                onError = { createErrorState(it) }
            )
    }

    private fun add(elementViewEntity: ElementViewEntity) {
        elementRepository.add(elementViewEntity.toElement())
            .fetchStateFullModel(
                onLoading = { createLoadingState() },
                onSuccess = {
                    elementId = it
                    createResultState(Result.ADDED)
                },
                onError = { createErrorState(it) }
            )
    }

    private fun update(elementViewEntity: ElementViewEntity) {
//        elementRepository.update(elementViewEntity.toElement(elementId))
//                .fetchStateFullModel(
//                        onLoading = { createLoadingState() },
//                        onSuccess = {
//                            elementId = it
//                            createResultState(Result.MODIFIED)
//                        },
//                        onError = { createErrorState(it) }
//                )
    }

    private fun copy(elementViewEntity: ElementViewEntity) {
//        elementRepository.copy(elementId, elementViewEntity.name)
//                .fetchStateFullModel(
//                        onLoading = { createLoadingState() },
//                        onSuccess = {
//                            elementId = it
//                            createResultState(Result.COPIED)
//                        },
//                        onError = { createErrorState(it) }
//                )
    }

    private fun createLoadingState() {
        viewState.updateValue(ManageElementViewState(isLoading = true))
    }

    private fun createInitialState(element: Element) {
        viewState.updateValue(
            ManageElementViewState(
                viewEntity = element.toViewEntity(),
                btnTextMessage = R.string.l_save,
                disableEveryFieldExceptName = requestType == RequestType.COPY
            )
        )
    }

    private fun createResultState(result: Result) {
        viewState.updateValue(ManageElementViewState(resultType = result))
    }

    private fun createErrorState(errorMessage: String?) {
        viewState.updateValue(ManageElementViewState(errorMessage = errorMessage))
    }

    private fun Element.toViewEntity() = ElementViewEntity(
        name = name,
        length = length.formattedValue,
        width = width.formattedValue,
        height = height.formattedValue
    )

    private fun ElementViewEntity.toElement() = ElementLight(
        name = name,
        length = length.toFloat(),
        width = width.toFloat(),
        height = height.toFloat(),
        wardrobeId = wardrobeId

    )

    private fun String.toFloat() = measureFormatter.toFloat(this)

    private val Float.formattedValue: String
        get() = measureFormatter.format(this)

    private fun LiveData<ManageElementViewState>.updateValue(elementViewState: ManageElementViewState) {
        (this as? MutableLiveData)?.value = elementViewState
    }
}

class ManageElementViewState(
    val isLoading: Boolean = false,
    val disableEveryFieldExceptName: Boolean = false,
    val viewEntity: ElementViewEntity? = null,
    val resultType: Result? = null,
    val errorMessage: String? = null,
    val btnTextMessage: Int = R.string.l_add
)