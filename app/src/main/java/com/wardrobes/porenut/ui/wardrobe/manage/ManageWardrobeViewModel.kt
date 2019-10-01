package com.wardrobes.porenut.ui.wardrobe.manage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.R
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.wardrobe.WardrobeRepository
import com.wardrobes.porenut.data.wardrobe.WardrobeRestRepository
import com.wardrobes.porenut.domain.CreationType
import com.wardrobes.porenut.domain.Wardrobe
import com.wardrobes.porenut.ui.common.DefaultMeasureFormatter
import com.wardrobes.porenut.ui.common.Event
import com.wardrobes.porenut.ui.common.MeasureFormatter
import com.wardrobes.porenut.ui.common.extension.updateValue

class ManageWardrobeViewModel(
    private val wardrobeRepository: WardrobeRepository = WardrobeRestRepository,
    private val measureFormatter: MeasureFormatter = DefaultMeasureFormatter
) : ViewModel() {
    val viewState: LiveData<ManageWardrobeViewState> = MutableLiveData<ManageWardrobeViewState>().apply {
        value = ManageWardrobeViewState()
    }
    val navigateBack: LiveData<Event<Unit>> = MutableLiveData()

    var wardrobeId: Long? = null
        set(value) {
            field = value?.also { fetchWardrobeDetails(it) }
        }
    var creationType: CreationType = CreationType.GENERATE

    fun manageWardrobe(viewEntity: ManageWardrobeViewEntity) {
        val wardrobe = viewEntity.toWardrobe()
        wardrobeId
            ?.also { updateWardrobe(wardrobe, it) }
            ?: addWardrobe(wardrobe)
    }

    private fun fetchWardrobeDetails(wardrobeId: Long) {
        wardrobeRepository.get(wardrobeId)
            .fetchStateFullModel(
                onLoading = { createLoadingState() },
                onSuccess = { createDetailsState(it) },
                onError = { createErrorState(it) }
            )
    }

    private fun addWardrobe(wardrobe: Wardrobe) {
        wardrobeRepository.add(wardrobe, creationType)
            .fetchStateFullModel(
                onLoading = { createLoadingState() },
                onSuccess = { navigateBack() },
                onError = { createErrorState(it) }
            )
    }

    private fun updateWardrobe(wardrobe: Wardrobe, wardrobeId: Long) {
        wardrobeRepository.update(wardrobeId, wardrobe)
            .fetchStateFullModel(
                onLoading = { createLoadingState() },
                onSuccess = { navigateBack() },
                onError = { createErrorState(it) }
            )
    }

    private fun createLoadingState() {
        viewState.updateValue(ManageWardrobeViewState(isLoading = true))
    }

    private fun createDetailsState(wardrobe: Wardrobe) {
        viewState.updateValue(ManageWardrobeViewState(viewEntity = wardrobe.toViewEntity(), btnActionText = R.string.l_save))
    }

    private fun createErrorState(errorMessage: String) {
        viewState.updateValue(ManageWardrobeViewState(errorMessage = errorMessage))
    }

    private fun navigateBack() {
        navigateBack.updateValue(Event(Unit))
    }

    private fun Wardrobe.toViewEntity() = ManageWardrobeViewEntity(
        symbol = symbol,
        width = width.format(),
        height = height.format(),
        depth = depth.format(),
        isUpper = type == Wardrobe.Type.UPPER
    )

    private fun ManageWardrobeViewEntity.toWardrobe() =
        Wardrobe(
            symbol = symbol,
            width = width.toFloat(),
            height = height.toFloat(),
            depth = depth.toFloat(),
            type = if (isUpper) Wardrobe.Type.UPPER else Wardrobe.Type.BOTTOM
        )

    private fun Float.format(): String = measureFormatter.format(this)

    private fun String.toFloat(): Float = measureFormatter.toFloat(this)
}

class ManageWardrobeViewState(
    val isLoading: Boolean = false,
    val viewEntity: ManageWardrobeViewEntity = ManageWardrobeViewEntity(),
    val errorMessage: String = "",
    val btnActionText: Int = R.string.l_add
)

data class ManageWardrobeViewEntity(
    val symbol: String = "",
    val width: String = "",
    val height: String = "",
    val depth: String = "",
    val isUpper: Boolean = false
)