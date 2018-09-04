package com.wardrobes.porenut.ui.v2.wardrobe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.R
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.element.ElementRepository
import com.wardrobes.porenut.data.element.ElementRestRepository
import com.wardrobes.porenut.data.wardrobe.WardrobeRepository
import com.wardrobes.porenut.data.wardrobe.WardrobeRestRepository
import com.wardrobes.porenut.domain.Element
import com.wardrobes.porenut.domain.Wardrobe
import com.wardrobes.porenut.ui.extension.updateValue
import com.wardrobes.porenut.ui.v2.element.ElementViewEntity
import com.wardrobes.porenut.ui.vo.DefaultMeasureFormatter
import com.wardrobes.porenut.ui.vo.Event
import com.wardrobes.porenut.ui.vo.MeasureFormatter

class WardrobeTabViewModel(
    private val wardrobeRepository: WardrobeRepository = WardrobeRestRepository,
    private val elementRepository: ElementRepository = ElementRestRepository,
    private val measureFormatter: MeasureFormatter = DefaultMeasureFormatter
) : ViewModel() {
    val viewState: LiveData<WardrobeTabViewState> = MutableLiveData<WardrobeTabViewState>().apply {
        value = WardrobeTabViewState(areDetailsVisible = true)
    }
    val detailViewState: LiveData<WardrobeDetailViewState> = MutableLiveData()
    val elementGroupViewState: LiveData<ElementGroupViewState> = MutableLiveData()
    val messageEvent: LiveData<Event<String>> = MutableLiveData()
    val navigateUpEvent: LiveData<Event<Unit>> = MutableLiveData()

    var wardrobeId: Long? = null

    private var wardrobe: Wardrobe? = null
    private var elementGroup: List<Element> = emptyList()

    fun showDetails() {
        viewState.updateValue(WardrobeTabViewState(areDetailsVisible = true))
        wardrobeId?.also { id ->
            wardrobeRepository.get(id)
                .fetchStateFullModel(
                    onLoading = {
                        detailViewState.updateValue(WardrobeDetailViewState(isLoading = true))
                    },
                    onSuccess = {
                        wardrobe = it
                        detailViewState.updateValue(WardrobeDetailViewState(viewEntity = it.toViewEntity()))
                    },
                    onError = {
                        messageEvent.updateValue(Event(it))
                        navigateUpEvent.updateValue(Event(Unit))
                    }
                )
        }
    }

    fun showElementGroup() {
        viewState.updateValue(WardrobeTabViewState(isElementGroupVisible = true))
        wardrobeId?.also { id ->
            elementRepository.getAll(id)
                .fetchStateFullModel(
                    onLoading = {
                        elementGroupViewState.updateValue(ElementGroupViewState(isLoading = true))
                    },
                    onSuccess = {
                        val isAddElementActionAvailable = wardrobe?.creationType == Wardrobe.CreationType.CUSTOM
                        elementGroup = it
                        if (it.isEmpty()) {
                            ElementGroupViewState(isEmptyListNotificationVisible = true, isAddElementActionAvailable = isAddElementActionAvailable)
                        } else {
                            ElementGroupViewState(viewEntities = it.map { it.toViewEntity() }, isAddElementActionAvailable = isAddElementActionAvailable)
                        }.also { elementGroupViewState.updateValue(it) }
                    },
                    onError = {
                        messageEvent.updateValue(Event(it))
                        elementGroupViewState.updateValue(ElementGroupViewState())
                    }
                )
        }
    }

    fun showGallery() {
        viewState.updateValue(WardrobeTabViewState(isGalleryVisible = true))
    }

    fun deleteWardrobe() {
        wardrobeId?.also {
            wardrobeRepository.delete(it)
                .fetchStateFullModel(
                    onLoading = {
                        detailViewState.value?.also {
                            detailViewState.updateValue(it.copy(isLoading = true))
                        }
                    },
                    onSuccess = {
                        messageEvent.updateValue(Event("Pomyślnie usunięto szafę!"))
                        navigateUpEvent.updateValue(Event(Unit))
                    },
                    onError = {
                        messageEvent.updateValue(Event(it))
                        detailViewState.value?.also {
                            detailViewState.updateValue(it.copy(isLoading = false))
                        }
                    }
                )
        }
    }

    fun getElementId(viewEntity: ElementViewEntity): Long = elementGroup.first { it.name == viewEntity.name }.id

    private fun Wardrobe.toViewEntity() =
        WardrobeDetailViewEntity(
            symbol = symbol,
            width = width.format(),
            height = height.format(),
            depth = depth.format(),
            type = type.text
        )

    private fun Element.toViewEntity() = ElementViewEntity(
        name = name,
        length = length.format(),
        width = width.format(),
        height = height.format()
    )

    private fun Float.format() = measureFormatter.format(this)

    private val Wardrobe.Type.text: Int
        get() = when (this) {
            Wardrobe.Type.STANDING -> R.string.l_standing
            Wardrobe.Type.HANGING -> R.string.l_hanging
        }
}

class WardrobeTabViewState(
    val areDetailsVisible: Boolean = false,
    val isElementGroupVisible: Boolean = false,
    val isGalleryVisible: Boolean = false
)

data class WardrobeDetailViewState(
    val isLoading: Boolean = false,
    val viewEntity: WardrobeDetailViewEntity? = null
)

class ElementGroupViewState(
    val isLoading: Boolean = false,
    val viewEntities: List<ElementViewEntity> = emptyList(),
    val isEmptyListNotificationVisible: Boolean = false,
    val isAddElementActionAvailable: Boolean = false
)

class WardrobeDetailViewEntity(
    val symbol: String,
    val width: String,
    val height: String,
    val depth: String,
    val type: Int
)