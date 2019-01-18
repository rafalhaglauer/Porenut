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
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class RelativeDrillingSetDetailsViewModel(
    private val relativeDrillingRepository: RelativeDrillingRepository = RelativeDrillingRestRepository,
    private val relativeDrillingSetRepository: RelativeDrillingSetRepository = RelativeDrillingSetRestRepository
) : ViewModel() {
    val viewState: LiveData<RelativeDrillingSetDetailsViewState> = MutableLiveData()
    val messageEvent: LiveData<Event<String>> = MutableLiveData()
    val navigateUpEvent: LiveData<Event<Unit>> = MutableLiveData()

    var drillingSetId: Long? = null
        set(value) {
            field = value?.also { fetchDetails(it) }
        }

    private var drillingGroup: List<RelativeDrilling> = emptyList()

    private fun fetchDetails(drillingSetId: Long) {
        Observable.zip(
            relativeDrillingSetRepository.get(drillingSetId),
            relativeDrillingRepository.getAll(drillingSetId),
            BiFunction { drillingSet: RelativeDrillingSet, drillingGroup: List<RelativeDrilling> -> Pair(drillingSet, drillingGroup) })
            .fetchStateFullModel(
                onLoading = { viewState.updateValue(RelativeDrillingSetDetailsViewState(isLoading = true)) },
                onSuccess = { (drillingSet, drillingGroup) ->
                    this.drillingGroup = drillingGroup
                    viewState.updateValue(
                        RelativeDrillingSetDetailsViewState(
                            drillingSetName = drillingSet.name,
                            drillings = drillingGroup.mapNotNull { it.id?.let { id -> RelativeDrillingGroupViewEntity(id, it.name) } },
                            isEmptyListNotificationVisible = drillingGroup.isEmpty()
                        )
                    )
                },
                onError = {
                    messageEvent.updateValue(Event(it))
                    navigateUpEvent.updateValue(Event(Unit))
                }
            )
    }

    fun delete() {
        drillingSetId?.also { id ->
            relativeDrillingSetRepository.delete(id)
                .fetchStateFullModel(
                    onLoading = { viewState.updateValue(RelativeDrillingSetDetailsViewState(isLoading = true)) },
                    onSuccess = {
                        messageEvent.updateValue(Event("Pomyślnie usunięto grupę wierceń!"))
                        navigateUpEvent.updateValue(Event(Unit))
                    },
                    onError = {
                        messageEvent.updateValue(Event(it))
                        fetchDetails(id)
                    }
                )
        }
    }
}

class RelativeDrillingSetDetailsViewState(
    val isLoading: Boolean = false,
    val drillingSetName: String = "",
    val drillings: List<RelativeDrillingGroupViewEntity> = emptyList(),
    val isEmptyListNotificationVisible: Boolean = false
)

class RelativeDrillingGroupViewEntity(val id: Long, val name: String)