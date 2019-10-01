package com.wardrobes.porenut.ui.wardrobe.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.wardrobe.WardrobeRepository
import com.wardrobes.porenut.data.wardrobe.WardrobeRestRepository
import com.wardrobes.porenut.domain.Wardrobe
import com.wardrobes.porenut.ui.common.DefaultMeasureFormatter
import com.wardrobes.porenut.ui.common.MeasureFormatter
import com.wardrobes.porenut.ui.common.extension.updateValue

class WardrobeGroupViewModel(
    private val wardrobeRepository: WardrobeRepository = WardrobeRestRepository,
    private val measureFormatter: MeasureFormatter = DefaultMeasureFormatter
) : ViewModel() {
    val viewState: LiveData<WardrobeGroupViewState> = MutableLiveData<WardrobeGroupViewState>()

    var wardrobeType: Wardrobe.Type = Wardrobe.Type.BOTTOM
        set(value) {
            field = value
            fetchWardrobes()
        }

    private var wardrobes: List<Wardrobe> = emptyList()

    fun getWardrobeId(viewEntity: WardrobeViewEntity) = wardrobes.first { it.symbol == viewEntity.symbol }.id

    private fun fetchWardrobes() {
        wardrobeRepository.getAll(wardrobeType)
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
        viewState.updateValue(WardrobeGroupViewState(isEmptyListNotificationVisible = wardrobes.isEmpty(), viewEntities = wardrobes.toViewEntity()))
    }

    private fun createErrorState(message: String) {
        viewState.updateValue(WardrobeGroupViewState(errorMessage = message))
    }

    private fun List<Wardrobe>.toViewEntity(): List<WardrobeViewEntity> = map {
        WardrobeViewEntity(it.symbol, it.width.format(), it.height.format(), it.depth.format())
    }

    private fun Float.format(): String = measureFormatter.format(this)

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
    val depth: String
)