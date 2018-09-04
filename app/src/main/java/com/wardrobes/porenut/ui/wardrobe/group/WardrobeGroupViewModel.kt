package com.wardrobes.porenut.ui.wardrobe.group

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.R
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.wardrobe.WardrobeRepository
import com.wardrobes.porenut.data.wardrobe.WardrobeRestRepository
import com.wardrobes.porenut.domain.Wardrobe
import com.wardrobes.porenut.ui.extension.updateValue
import com.wardrobes.porenut.ui.vo.DefaultMeasureFormatter
import com.wardrobes.porenut.ui.vo.MeasureFormatter

class WardrobeGroupViewModel(
    private val wardrobeRepository: WardrobeRepository = WardrobeRestRepository,
    private val measureFormatter: MeasureFormatter = DefaultMeasureFormatter
) : ViewModel() {
    val viewState: LiveData<WardrobeGroupViewState> = MutableLiveData<WardrobeGroupViewState>()

    var creationType: Wardrobe.CreationType = Wardrobe.CreationType.STANDARD
        set(value) {
            field = value
            fetchWardrobes()
        }

    private var wardrobes: List<Wardrobe> = emptyList()

    fun refresh() {
        fetchWardrobes()
    }

    fun getWardrobeId(viewEntity: WardrobeViewEntity) = wardrobes.first { it.symbol == viewEntity.symbol }.id

    private fun fetchWardrobes() {
        wardrobeRepository.getAll(creationType)
            .fetchStateFullModel(
                onLoading = { createLoadingState() },
                onSuccess = {
                    wardrobes = it
                    createSuccessState(it)
                },
                onError = { createErrorState(it) }
            )
    }

    private fun createLoadingState() {
        viewState.updateValue(WardrobeGroupViewState(isLoading = true))
    }

    private fun createSuccessState(wardrobes: List<Wardrobe>) {
        if (wardrobes.isEmpty()) {
            WardrobeGroupViewState(isEmptyListNotificationVisible = true)
        } else {
            WardrobeGroupViewState(viewEntities = wardrobes.toViewEntity())
        }.also {
            viewState.updateValue(it)
        }
    }

    private fun createErrorState(message: String) {
        viewState.updateValue(WardrobeGroupViewState(errorMessage = message))
    }

    private fun List<Wardrobe>.toViewEntity(): List<WardrobeViewEntity> = map {
        WardrobeViewEntity(it.symbol, it.width.format(), it.height.format(), it.depth.format(), it.type.icon)
    }

    private fun Float.format(): String = measureFormatter.format(this)

    private val Wardrobe.Type.icon: Int
        get() = when (this) {
            Wardrobe.Type.HANGING -> R.drawable.ic_wardrobe_hanging
            Wardrobe.Type.STANDING -> R.drawable.ic_wardrobe_standing
        }
}

class WardrobeGroupViewState(
    val isLoading: Boolean = false,
    val viewEntities: List<WardrobeViewEntity> = emptyList(),
    val isEmptyListNotificationVisible: Boolean = false,
    val errorMessage: String = ""
)

data class WardrobeViewEntity(
    val symbol: String,
    val width: String,
    val height: String,
    val depth: String,
    @DrawableRes val icon: Int
)