package com.wardrobes.porenut.ui.pattern.wardrobe.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.pattern.wardrobe.WardrobePatternRepository
import com.wardrobes.porenut.data.pattern.wardrobe.WardrobePatternRestRepository
import com.wardrobes.porenut.domain.WardrobePattern
import com.wardrobes.porenut.ui.common.Event
import com.wardrobes.porenut.ui.common.extension.updateValue

class WardrobePatternGroupViewModel(
    private val wardrobePatternRepository: WardrobePatternRepository = WardrobePatternRestRepository
) : ViewModel() {

    val viewState: LiveData<WardrobePatternGroupViewState> = MutableLiveData()
    val errorEvent: LiveData<Event<String>> = MutableLiveData()
    val showDetailsEvent: LiveData<Event<String>> = MutableLiveData()
    val addDescriptionEvent: LiveData<Event<String>> = MutableLiveData()

    private var patterns: List<WardrobePattern> = emptyList()

    fun loadWardrobes() {
        wardrobePatternRepository.getAll()
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

    fun showDetails(viewEntity: WardrobePatternViewEntity) {
        showLoading(true)
        showDetailsEvent.updateValue(Event(findWardrobe(viewEntity).id))
    }

    fun addDescription(viewEntity: WardrobePatternViewEntity) {
        showLoading(true)
        addDescriptionEvent.updateValue(Event(findWardrobe(viewEntity).id))
    }

    private fun showLoading(isLoading: Boolean) {
        viewState.updateValue(WardrobePatternGroupViewState(isLoading = isLoading))
    }

    private fun showPatterns(wardrobes: List<WardrobePattern>) {
        viewState.updateValue(
            WardrobePatternGroupViewState(
                isEmptyListNotificationVisible = wardrobes.isEmpty(),
                viewEntities = map(wardrobes)
            )
        )
    }

    private fun showError(message: String) {
        errorEvent.updateValue(Event(message))
    }

    private fun findWardrobe(viewEntity: WardrobePatternViewEntity) = patterns.first { it.symbol == viewEntity.symbol }

    private fun map(wardrobes: List<WardrobePattern>): List<WardrobePatternViewEntity> = wardrobes.map {
        WardrobePatternViewEntity(it.symbol, it.description)
    }

}

class WardrobePatternGroupViewState(
    val isLoading: Boolean = false,
    val viewEntities: List<WardrobePatternViewEntity> = emptyList(),
    val isEmptyListNotificationVisible: Boolean = false
)

data class WardrobePatternViewEntity(
    val symbol: String,
    val description: String
) {
    // TODO val!
    var isAddDescriptionOptionVisible: Boolean = description.isEmpty()
}