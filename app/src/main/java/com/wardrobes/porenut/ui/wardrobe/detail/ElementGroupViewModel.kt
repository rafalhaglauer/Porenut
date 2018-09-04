//package com.wardrobes.porenut.ui.wardrobe.detail
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.wardrobes.porenut.api.extension.fetchStateFullModel
//import com.wardrobes.porenut.data.element.ElementRepository
//import com.wardrobes.porenut.data.element.ElementRestRepository
//import com.wardrobes.porenut.domain.Element
//import com.wardrobes.porenut.domain.UNDEFINED_ID
//import com.wardrobes.porenut.domain.Wardrobe
//import com.wardrobes.porenut.ui.vo.DefaultMeasureFormatter
//import com.wardrobes.porenut.ui.vo.MeasureFormatter
//
//class ElementGroupViewModel(
//    private val elementRepository: ElementRepository = ElementRestRepository,
//    private val measureFormatter: MeasureFormatter = DefaultMeasureFormatter
//) : ViewModel() {
//    val viewState: LiveData<ElementGroupViewState> = MutableLiveData<ElementGroupViewState>()
//
//    var creationType: Wardrobe.CreationType? = null
//
//    var wardrobeId: Long = UNDEFINED_ID
//        set(value) {
//            field = value
//            fetchElements()
//        }
//
//    var selectedElementId: Long = UNDEFINED_ID
//
//    private var elements: List<Element> = emptyList()
//
//    fun refresh() {
//        fetchElements()
//    }
//
//    fun selectElement(viewEntity: ElementViewEntity) {
//        elements.find { it.toViewEntity() == viewEntity }?.also { selectedElementId = it.id }
//    }
//
//    private fun fetchElements() {
//        elementRepository.getAll(wardrobeId)
//            .fetchStateFullModel(
//                onLoading = { createLoadingState() },
//                onSuccess = { createSuccessState(it) },
//                onError = { createErrorState(it) }
//            )
//    }
//
//    private fun createLoadingState() {
//        updateState(ElementGroupViewState(isLoading = true))
//    }
//
//    private fun createSuccessState(elements: List<Element>) {
//        this.elements = elements
//        updateState(
//            ElementGroupViewState(
//                viewEntities = elements.toViewEntities(),
//                isAddButtonVisible = creationType == Wardrobe.CreationType.CUSTOM
//            )
//        )
//    }
//
//    private fun createErrorState(errorMessage: String?) {
//        updateState(ElementGroupViewState(errorMessage = errorMessage))
//    }
//
//    private fun updateState(state: ElementGroupViewState) {
//        (viewState as MutableLiveData).value = state
//    }
//
//    private fun List<Element>.toViewEntities() = map { it.toViewEntity() }
//
//    private fun Element.toViewEntity() = ElementViewEntity(
//        name = name,
//        length = length.formattedValue,
//        width = width.formattedValue,
//        height = height.formattedValue
//    )
//
//    private val Float.formattedValue: String
//        get() = measureFormatter.format(this)
//}
