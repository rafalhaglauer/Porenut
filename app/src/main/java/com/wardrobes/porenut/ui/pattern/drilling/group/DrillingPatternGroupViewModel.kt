package com.wardrobes.porenut.ui.pattern.drilling.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.pattern.drilling.DrillingPatternRepository
import com.wardrobes.porenut.data.pattern.drilling.DrillingPatternRestRepository
import com.wardrobes.porenut.domain.DrillingPattern
import com.wardrobes.porenut.ui.common.Event
import com.wardrobes.porenut.ui.common.extension.updateValue

class DrillingPatternGroupViewModel(
    private val drillingPatternRepository: DrillingPatternRepository = DrillingPatternRestRepository
) : ViewModel() {

    val viewState: LiveData<DrillingPatternGroupViewState> = MutableLiveData()
    val errorEvent: LiveData<Event<String>> = MutableLiveData()
    val showDetailsEvent: LiveData<Event<String>> = MutableLiveData()

    private var patterns: List<DrillingPattern> = emptyList()

    fun loadDrillings(wardrobePatternId: String) {
        drillingPatternRepository.getAll(wardrobePatternId)
            .fetchStateFullModel(
                onLoading = { showLoading(true) },
                onSuccess = {
                    patterns = it
                    showPatterns(it)
                },
                onError = {
                    showLoading(false)
                    showError(it)
                }
            )
    }

    fun showDetails(viewEntity: DrillingPatternViewEntity) {
        showLoading(true)
        showDetailsEvent.updateValue(Event(findDrilling(viewEntity).id))
    }

    private fun showLoading(isLoading: Boolean) {
        viewState.updateValue(DrillingPatternGroupViewState(isLoading = isLoading))
    }

    private fun showPatterns(drillings: List<DrillingPattern>) {
        viewState.updateValue(
            DrillingPatternGroupViewState(
                isEmptyListNotificationVisible = drillings.isEmpty(),
                viewEntities = map(drillings)
            )
        )
    }

    private fun showError(message: String) {
        errorEvent.updateValue(Event(message))
    }

    private fun findDrilling(viewEntity: DrillingPatternViewEntity) = patterns.first { it.name == viewEntity.name }

    private fun map(drillings: List<DrillingPattern>): List<DrillingPatternViewEntity> = drillings.map {
        DrillingPatternViewEntity(it.name)
    }

}

class DrillingPatternGroupViewState(
    val isLoading: Boolean = false,
    val viewEntities: List<DrillingPatternViewEntity> = emptyList(),
    val isEmptyListNotificationVisible: Boolean = false
)

data class DrillingPatternViewEntity(
    val name: String
)