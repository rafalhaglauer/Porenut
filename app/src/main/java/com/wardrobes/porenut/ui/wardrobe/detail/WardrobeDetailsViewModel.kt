package com.wardrobes.porenut.ui.wardrobe.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.R
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.wardrobe.WardrobeRepository
import com.wardrobes.porenut.data.wardrobe.WardrobeRestRepository
import com.wardrobes.porenut.domain.UNDEFINED_ID
import com.wardrobes.porenut.domain.Wardrobe
import com.wardrobes.porenut.ui.vo.DefaultMeasureFormatter
import com.wardrobes.porenut.ui.vo.MeasureFormatter

class WardrobeDetailsViewModel(
    private val wardrobeRepository: WardrobeRepository = WardrobeRestRepository,
    private val measureFormatter: MeasureFormatter = DefaultMeasureFormatter
) : ViewModel() {
    val viewState: LiveData<WardrobeDetailsViewState> = MutableLiveData<WardrobeDetailsViewState>()

    var wardrobeId: Long = UNDEFINED_ID
        set(value) {
            field = value
            fetchWardrobeDetails(value)
        }

    fun delete() {
        wardrobeRepository.delete(wardrobeId)
            .fetchStateFullModel(
                onLoading = { createLoadingState() },
                onSuccess = { createDeletedState() },
                onError = { createErrorState(it) }
            )
    }

    private fun fetchWardrobeDetails(wardrobeId: Long) {
        wardrobeRepository.get(wardrobeId)
            .fetchStateFullModel(
                onLoading = { createLoadingState() },
                onSuccess = { createSuccessState(it) },
                onError = { createErrorState(it) }
            )
    }

    private fun createLoadingState() {
        setState(WardrobeDetailsViewState(isLoading = true))
    }

    private fun createSuccessState(wardrobe: Wardrobe) {
        setState(WardrobeDetailsViewState(wardrobeViewEntity = wardrobe.toViewEntity()))
    }

    private fun createDeletedState() {
        setState(WardrobeDetailsViewState(isDeleted = true))
    }

    private fun createErrorState(errorMessage: String) {
        setState(WardrobeDetailsViewState(errorMessage = errorMessage))
    }

    private fun setState(viewState: WardrobeDetailsViewState) {
        (this@WardrobeDetailsViewModel.viewState as MutableLiveData).value = viewState
    }

    private fun Wardrobe.toViewEntity() = WardrobeDetailViewEntity(
        symbol = symbol,
        width = width.format(),
        height = height.format(),
        depth = depth.format(),
        type = type.text
    )

    private fun Float.format() = measureFormatter.format(this)
    private val Wardrobe.Type.text: Int
        get() = when (this) {
            Wardrobe.Type.STANDING -> R.string.l_standing
            Wardrobe.Type.HANGING -> R.string.l_hanging
        }
}

class WardrobeDetailsViewState(
    val isLoading: Boolean = false,
    val wardrobeViewEntity: WardrobeDetailViewEntity = WardrobeDetailViewEntity.empty(),
    val errorMessage: String = "",
    val isDeleted: Boolean = false
)

data class WardrobeDetailViewEntity(
    val symbol: String,
    val width: String,
    val height: String,
    val depth: String,
    val type: Int
) {
    companion object {

        fun empty() = WardrobeDetailViewEntity("", "", "", "", 0)
    }
}