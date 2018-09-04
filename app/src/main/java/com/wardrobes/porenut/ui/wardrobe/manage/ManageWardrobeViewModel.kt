package com.wardrobes.porenut.ui.wardrobe.manage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.R
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.wardrobe.WardrobeRepository
import com.wardrobes.porenut.data.wardrobe.WardrobeRestRepository
import com.wardrobes.porenut.domain.UNDEFINED_ID
import com.wardrobes.porenut.domain.Wardrobe
import com.wardrobes.porenut.domain.WardrobeLight
import com.wardrobes.porenut.ui.vo.DefaultMeasureFormatter
import com.wardrobes.porenut.ui.vo.MeasureFormatter
import com.wardrobes.porenut.ui.vo.RequestType
import com.wardrobes.porenut.ui.vo.Result

class ManageWardrobeViewModel(
    private val wardrobeRepository: WardrobeRepository = WardrobeRestRepository,
    private val measureFormatter: MeasureFormatter = DefaultMeasureFormatter
) : ViewModel() {
    val viewState: LiveData<ManageWardrobeViewState> = MutableLiveData<ManageWardrobeViewState>()

    var creationType: Wardrobe.CreationType? = null

    var wardrobeId: Long = UNDEFINED_ID

    var requestType: RequestType? = null
        set(value) {
            field = value
            when (value) {
                RequestType.ADD -> setViewState(ManageWardrobeViewState(viewEntity = ManageWardrobeViewEntity.empty()))
                RequestType.COPY -> fetchWardrobeDetails(wardrobeId)
                RequestType.EDIT -> fetchWardrobeDetails(wardrobeId)
            }
        }

    var viewEntity: ManageWardrobeViewEntity = ManageWardrobeViewEntity.empty()
        set(value) {
            field = value
            manageWardrobe()
        }

    private fun fetchWardrobeDetails(wardrobeId: Long) {
        wardrobeRepository.get(wardrobeId)
            .fetchStateFullModel(
                onLoading = { createLoadingState() },
                onSuccess = { createDetailsState(it) },
                onError = { createErrorState(it) }
            )
    }

    private fun manageWardrobe() {
        when (requestType) {
            RequestType.ADD -> addWardrobe()
            RequestType.EDIT -> updateWardrobe()
            RequestType.COPY -> copyWardrobe()
        }
    }

    private fun addWardrobe() {
        creationType?.also { creationType ->
            wardrobeRepository.add(viewEntity.toWardrobe(creationType))
                .fetchStateFullModel(
                    onLoading = { createLoadingState() },
                    onSuccess = { createFinishState(it) },
                    onError = { createErrorState(it) }
                )
        }
    }

    private fun updateWardrobe() {
        creationType?.also { creationType ->
            wardrobeRepository.update(wardrobeId, viewEntity.toWardrobe(creationType))
                .fetchStateFullModel(
                    onLoading = { createLoadingState() },
                    onSuccess = { createFinishState(wardrobeId) },
                    onError = { createErrorState(it) }
                )
        }
    }

    private fun copyWardrobe() {
//        wardrobeRepository.copy(wardrobeId, viewEntities.symbol)
//                .fetchStateFullModel(
//                        onLoading = { createLoadingState() },
//                        onSuccess = { createFinishState(it) },
//                        onError = { createErrorState(it) }
//                )
    }

    private fun createLoadingState() {
        setViewState(ManageWardrobeViewState(isLoading = true))
    }

    private fun createDetailsState(wardrobe: Wardrobe) {
        creationType = wardrobe.creationType
        setViewState(
            ManageWardrobeViewState(
                viewEntity = wardrobe.toViewEntity(),
                disableEveryFieldExceptSymbol = requestType == RequestType.COPY,
                btnActionText = if (requestType == RequestType.EDIT) R.string.l_save else R.string.l_copy
            )
        )
    }

    private fun createFinishState(wardrobeId: Long) {
        this.wardrobeId = wardrobeId
        setViewState(ManageWardrobeViewState(resultType = requestType?.resultType))
    }

    private fun createErrorState(errorMessage: String) {
        setViewState(ManageWardrobeViewState(errorMessage = errorMessage))
    }

    private fun setViewState(viewState: ManageWardrobeViewState) {
        (this@ManageWardrobeViewModel.viewState as MutableLiveData).value = viewState
    }

    private fun Wardrobe.toViewEntity() = ManageWardrobeViewEntity(
        symbol = symbol,
        width = width.format(),
        height = height.format(),
        depth = depth.format(),
        isHanging = type.isHanging
    )

    private fun ManageWardrobeViewEntity.toWardrobe(creationType: Wardrobe.CreationType) =
        WardrobeLight(
            symbol = symbol,
            width = width.toFloat(),
            height = height.toFloat(),
            depth = depth.toFloat(),
            type = isHanging.wardrobeType,
            creationType = creationType
        )

    private fun Float.format(): String = measureFormatter.format(this)

    private fun String.toFloat(): Float = measureFormatter.toFloat(this)

    private val Wardrobe.Type.isHanging: Boolean
        get() = when (this) {
            Wardrobe.Type.HANGING -> true
            Wardrobe.Type.STANDING -> false
        }

    private val Boolean.wardrobeType: Wardrobe.Type
        get() = when (this) {
            true -> Wardrobe.Type.HANGING
            false -> Wardrobe.Type.STANDING
        }

    private val RequestType.resultType: Result
        get() = when (this) {
            RequestType.ADD -> Result.ADDED
            RequestType.COPY -> Result.COPIED
            RequestType.EDIT -> Result.MODIFIED
            RequestType.DELETE -> Result.DELETED
        }
}

class ManageWardrobeViewState(
    val isLoading: Boolean = false,
    val viewEntity: ManageWardrobeViewEntity = ManageWardrobeViewEntity.empty(),
    val errorMessage: String = "",
    val btnActionText: Int = R.string.l_add,
    val disableEveryFieldExceptSymbol: Boolean = false,
    val resultType: Result? = null
)

data class ManageWardrobeViewEntity(
    val symbol: String,
    val width: String,
    val height: String,
    val depth: String,
    val isHanging: Boolean
) {
    companion object {

        fun empty() = ManageWardrobeViewEntity("", "", "", "", false)
    }
}