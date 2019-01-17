package com.wardrobes.porenut.ui.wardrobe.detail

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
import com.wardrobes.porenut.ui.vo.Event
import com.wardrobes.porenut.ui.vo.MeasureFormatter

class WardrobeDetailViewModel(
    private val wardrobeRepository: WardrobeRepository = WardrobeRestRepository,
    private val measureFormatter: MeasureFormatter = DefaultMeasureFormatter
) : ViewModel() {
    val viewState: LiveData<WardrobeDetailViewState> = MutableLiveData()
    val messageEvent: LiveData<Event<String>> = MutableLiveData()
    val navigateUpEvent: LiveData<Event<Unit>> = MutableLiveData()

    var wardrobeId: Long? = null
        set(value) {
            field = value?.also { fetchDetails(it) }
        }

    private fun fetchDetails(wardrobeId: Long) {
        wardrobeRepository.get(wardrobeId)
            .fetchStateFullModel(
                onLoading = {
                    viewState.updateValue(WardrobeDetailViewState(isLoading = true))
                },
                onSuccess = {
                    viewState.updateValue(WardrobeDetailViewState(viewEntity = it.toViewEntity()))
                },
                onError = {
                    messageEvent.updateValue(Event(it))
                    navigateUpEvent.updateValue(Event(Unit))
                }
            )
    }

    private fun Wardrobe.toViewEntity() =
        WardrobeDetailViewEntity(
            symbol = symbol,
            width = width.format(),
            height = height.format(),
            depth = depth.format(),
            type = type.text
        )

    private fun Float.format() = measureFormatter.format(this)

    private val Wardrobe.Type.text: Int
        get() = when (this) {
            Wardrobe.Type.BOTTOM -> R.string.l_bottom
            Wardrobe.Type.UPPER -> R.string.l_upper
        }
}

data class WardrobeDetailViewState(
    val isLoading: Boolean = false,
    val viewEntity: WardrobeDetailViewEntity = WardrobeDetailViewEntity()
)

class WardrobeDetailViewEntity(
    val symbol: String = "",
    val width: String = "",
    val height: String = "",
    val depth: String = "",
    val type: Int = R.string.l_bottom
)