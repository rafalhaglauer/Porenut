package com.wardrobes.porenut.ui.wardrobe.group.model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.support.annotation.DrawableRes
import com.wardrobes.porenut.R
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.wardrobe.WardrobeRepository
import com.wardrobes.porenut.data.wardrobe.WardrobeRestRepository
import com.wardrobes.porenut.domain.Wardrobe
import com.wardrobes.porenut.ui.vo.DefaultMeasureFormatter
import com.wardrobes.porenut.ui.vo.MeasureFormatter

class WardrobeGroupViewModel(
        private val wardrobeRepository: WardrobeRepository = WardrobeRestRepository,
        private val measureFormatter: MeasureFormatter = DefaultMeasureFormatter
) : ViewModel() {
    private var _viewState: MutableLiveData<WardrobeGroupViewState>? = null
    val viewState: MutableLiveData<WardrobeGroupViewState>
        get() {
            if (_viewState == null) {
                _viewState = MutableLiveData()
                fetchWardrobes()
            }
            return _viewState as MutableLiveData<WardrobeGroupViewState>
        }

    var creationType: Wardrobe.CreationType = Wardrobe.CreationType.STANDARD
        set(value) {
            field = value
            if (_viewState != null) fetchWardrobes()
        }

    private fun fetchWardrobes() {
        wardrobeRepository.getAll()
                .fetchStateFullModel(
                        onLoading = { createLoadingState() },
                        onSuccess = { createSuccessState(it) },
                        onError = { createErrorState(it) }
                )
    }

    private fun createLoadingState() {
        viewState.value = WardrobeGroupViewState(isLoading = true)
    }

    private fun createSuccessState(wardrobes: List<Wardrobe>) {
        viewState.value = WardrobeGroupViewState(viewEntity = wardrobes.toViewEntity())
    }

    private fun createErrorState(message: String?) {
        viewState.value = WardrobeGroupViewState(errorMessage = message ?: "")
    }

    private fun List<Wardrobe>.toViewEntity(): List<WardrobeViewEntity> = map {
        WardrobeViewEntity(it.symbol, it.width.format(), it.height.format(), it.depth.format(), it.type.icon)
    }

    private fun Float.format(): String = measureFormatter.format(this)

    private val Wardrobe.Type.icon: Int
        get() = when (this) {
            Wardrobe.Type.HANGING -> R.drawable.ic_launcher_background
            Wardrobe.Type.STANDING -> R.drawable.ic_launcher_background
        }
}

class WardrobeGroupViewState(
        var isLoading: Boolean = false,
        var viewEntity: List<WardrobeViewEntity> = emptyList(),
        var errorMessage: String = ""
)

data class WardrobeViewEntity(
        val symbol: String,
        val width: String,
        val height: String,
        val depth: String,
        @DrawableRes val icon: Int)