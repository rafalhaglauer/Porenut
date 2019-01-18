package com.wardrobes.porenut.ui.composition.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.relative.RelativeDrillingRepository
import com.wardrobes.porenut.data.relative.RelativeDrillingRestRepository
import com.wardrobes.porenut.data.relative.RelativeDrillingSetRepository
import com.wardrobes.porenut.data.relative.RelativeDrillingSetRestRepository
import com.wardrobes.porenut.domain.RelativeDrilling
import com.wardrobes.porenut.domain.RelativeDrillingSet
import com.wardrobes.porenut.ui.extension.updateValue
import com.wardrobes.porenut.ui.vo.Event

class RelativeCompositionTabViewModel(
    private val relativeDrillingRepository: RelativeDrillingRepository = RelativeDrillingRestRepository,
    private val relativeDrillingCompositionRepository: RelativeDrillingSetRepository = RelativeDrillingSetRestRepository


) : ViewModel() {
    val viewState: LiveData<RelativeCompositionTabViewState> = MutableLiveData()
    val drillingGroupViewState: LiveData<RelativeDrillingGroupViewState> = MutableLiveData()
    val detailViewState: LiveData<RelativeCompositionDetailViewState> = MutableLiveData()
    val messageEvent: LiveData<Event<String>> = MutableLiveData()
    val navigateUpEvent: LiveData<Event<Unit>> = MutableLiveData()

    var relativeCompositionId: Long? = null

    private var relativeDrillingGroup: List<RelativeDrilling> = emptyList()

    fun showDetails() {
        viewState.updateValue(RelativeCompositionTabViewState(areDetailsVisible = true))
        relativeCompositionId?.also {
            relativeDrillingCompositionRepository.get(it)
                .fetchStateFullModel(
                    onLoading = {
                        detailViewState.updateValue(
                            RelativeCompositionDetailViewState(
                                isLoading = true
                            )
                        )
                    },
                    onSuccess = {
                        detailViewState.updateValue(RelativeCompositionDetailViewState(viewEntity = it.toViewEntity()))
                    },
                    onError = {
                        messageEvent.updateValue(Event(it))
                        navigateUpEvent.updateValue(Event(Unit))
                    }
                )
        }
    }

    fun showDrillingGroup() {
        viewState.updateValue(RelativeCompositionTabViewState(isDrillingGroupVisible = true))
        relativeCompositionId?.also { id ->
            relativeDrillingRepository.getAll(id)
                .fetchStateFullModel(
                    onLoading = {
                        drillingGroupViewState.updateValue(
                            RelativeDrillingGroupViewState(
                                isLoading = true
                            )
                        )
                    },
                    onSuccess = {
                        relativeDrillingGroup = it
                        if (it.isEmpty()) {
                            RelativeDrillingGroupViewState(isEmptyListNotificationVisible = true)
                        } else {
                            RelativeDrillingGroupViewState(drillingNames = it.map { it.name })
                        }.also { drillingGroupViewState.updateValue(it) }
                    },
                    onError = {
                        messageEvent.updateValue(Event(it))
                        drillingGroupViewState.updateValue(RelativeDrillingGroupViewState())
                    }
                )
        }
    }

    fun getDrillingId(drillingName: String): Long =
        relativeDrillingGroup.first { it.name == drillingName }.id

    fun delete() {
        relativeCompositionId?.also {
            relativeDrillingCompositionRepository.delete(it)
                .fetchStateFullModel(
                    onLoading = {
                        detailViewState.updateValue(
                            RelativeCompositionDetailViewState(
                                isLoading = true
                            )
                        )
                    },
                    onSuccess = {
                        messageEvent.updateValue(Event("Pomyślnie usunięto grupę wierceń!"))
                        navigateUpEvent.updateValue(Event(Unit))
                    },
                    onError = {
                        messageEvent.updateValue(Event(it))
                        showDetails()
                    }
                )
        }
    }

    private fun RelativeDrillingSet.toViewEntity(): RelativeCompositionDetailViewEntity {
        return RelativeCompositionDetailViewEntity(name = name)
    }
}

/*
* class ElementTabViewModel() : ViewModel() {

    fun deleteElement() {
        elementId?.also {
            elementRepository.delete(it)
                .fetchStateFullModel(
                    onLoading = {
                        elementDetailViewState.value?.also {
                            elementDetailViewState.updateValue(it.copy(isLoading = true))
                        }
                    },
                    onSuccess = {
                        errorEvent.updateValue(Event("Pomyślnie usunięto element!"))
                        navigateUpEvent.updateValue(Event(Unit))
                    },
                    onError = {
                        errorEvent.updateValue(Event(it))
                        elementDetailViewState.value?.also {
                            elementDetailViewState.updateValue(it.copy(isLoading = false))
                        }
                    }
                )
        }
    }

    fun getDrillingId(viewEntity: DrillingGroupViewEntity): Long = drillingGroup.first { it.toViewEntity() == viewEntity }.id
*/

class RelativeCompositionTabViewState(
    val areDetailsVisible: Boolean = false,
    val isDrillingGroupVisible: Boolean = false
)

data class RelativeCompositionDetailViewState(
    val isLoading: Boolean = false,
    val viewEntity: RelativeCompositionDetailViewEntity? = null
)

class RelativeCompositionDetailViewEntity(val name: String)

class RelativeDrillingGroupViewState(
    val isLoading: Boolean = false,
    val drillingNames: List<String> = emptyList(),
    val isEmptyListNotificationVisible: Boolean = false
)