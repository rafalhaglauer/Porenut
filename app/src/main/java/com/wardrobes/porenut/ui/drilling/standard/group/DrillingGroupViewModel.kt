package com.wardrobes.porenut.ui.drilling.standard.group


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.drilling.DrillingRepository
import com.wardrobes.porenut.data.drilling.DrillingRestRepository
import com.wardrobes.porenut.domain.CreationType
import com.wardrobes.porenut.domain.Drilling
import com.wardrobes.porenut.ui.extension.updateValue
import com.wardrobes.porenut.ui.vo.DefaultMeasureFormatter
import com.wardrobes.porenut.ui.vo.Event
import com.wardrobes.porenut.ui.vo.MeasureFormatter

class DrillingGroupViewModel(
    private val drillingRepository: DrillingRepository = DrillingRestRepository,
    private val measureFormatter: MeasureFormatter = DefaultMeasureFormatter
) : ViewModel() {
    val viewState: LiveData<DrillingGroupViewState> = MutableLiveData()
    val messageEvent: LiveData<Event<String>> = MutableLiveData()
    val navigateUpEvent: LiveData<Event<Unit>> = MutableLiveData()

    var elementId: Long? = null
        set(value) {
            field = value?.also { showDrillingGroup(it) }
        }

    private var drillingGroup: List<Drilling> = emptyList()

    private fun showDrillingGroup(elementId: Long) {
        drillingRepository.getAll(elementId)
            .fetchStateFullModel(
                onLoading = { viewState.updateValue(DrillingGroupViewState(isLoading = true)) },
                onSuccess = {
                    drillingGroup = it
                    viewState.updateValue(
                        DrillingGroupViewState(isEmptyListNotificationVisible = it.isEmpty(), viewEntities = it.toViewEntities())
                    )
                },
                onError = {
                    messageEvent.updateValue(Event(it))
                    navigateUpEvent.updateValue(Event(Unit))
                }
            )
    }

    fun getDrillingId(viewEntity: DrillingGroupViewEntity): Long = drillingGroup.first { it.toViewEntity() == viewEntity }.id

    private fun List<Drilling>.toViewEntities() = map { it.toViewEntity() }

    private fun Drilling.toViewEntity() = DrillingGroupViewEntity(
        xPosition = xPosition.formattedValue,
        yPosition = yPosition.formattedValue,
        diameter = diameter.formattedValue,
        depth = depth.formattedValue,
        isBlocked = type == CreationType.GENERATE
    )

    private val Float.formattedValue: String
        get() = measureFormatter.format(this)
}

class DrillingGroupViewState(
    val isLoading: Boolean = false,
    val viewEntities: List<DrillingGroupViewEntity> = emptyList(),
    val isEmptyListNotificationVisible: Boolean = false
)

data class DrillingGroupViewEntity(
    val xPosition: String,
    val yPosition: String,
    val diameter: String,
    val depth: String,
    val isBlocked: Boolean
)