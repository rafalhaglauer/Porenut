package com.wardrobes.porenut.ui.wardrobe.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.wardrobe.WardrobeRepository
import com.wardrobes.porenut.data.wardrobe.WardrobeRestRepository
import com.wardrobes.porenut.domain.Wardrobe
import com.wardrobes.porenut.ui.common.Event
import com.wardrobes.porenut.ui.common.extension.updateValue

class WardrobeGroupViewModel(
    private val wardrobeRepository: WardrobeRepository = WardrobeRestRepository
) : ViewModel() {

    val viewState: LiveData<WardrobeGroupViewState> = MutableLiveData()
    val errorEvent: LiveData<Event<String>> = MutableLiveData()
    val showDetailsEvent: LiveData<Event<String>> = MutableLiveData()
    val addDescriptionEvent: LiveData<Event<String>> = MutableLiveData()

    private var wardrobes: List<Wardrobe> = emptyList()

    fun loadWardrobes() {
        wardrobeRepository.getAll()
            .fetchStateFullModel(
                onLoading = { showLoading(true) },
                onSuccess = {
                    wardrobes = it
                    showWardrobes(it)
                },
                onError = {
                    showLoading(false)
                    showError(it)
                }
            )
    }

    fun showDetails(viewEntity: WardrobeViewEntity) {
        showLoading(true)
        showDetailsEvent.updateValue(Event(findWardrobe(viewEntity).id))
    }

    fun addDescription(viewEntity: WardrobeViewEntity) {
        showLoading(true)
        addDescriptionEvent.updateValue(Event(findWardrobe(viewEntity).id))
    }

    private fun showLoading(isLoading: Boolean) {
        viewState.updateValue(WardrobeGroupViewState(isLoading = isLoading))
    }

    private fun showWardrobes(wardrobes: List<Wardrobe>) {
        viewState.updateValue(WardrobeGroupViewState(isEmptyListNotificationVisible = wardrobes.isEmpty(), viewEntities = map(wardrobes)))
    }

    private fun showError(message: String) {
        errorEvent.updateValue(Event(message))
    }

    private fun findWardrobe(viewEntity: WardrobeViewEntity) = wardrobes.first { it.symbol == viewEntity.symbol }

    private fun map(wardrobes: List<Wardrobe>): List<WardrobeViewEntity> = wardrobes.map {
        WardrobeViewEntity(it.symbol, if (it.symbol == "DSG") "Jakiś krótki opis szafy." else "") // TODO!
    }

}

class WardrobeGroupViewState(
    val isLoading: Boolean = false,
    val viewEntities: List<WardrobeViewEntity> = emptyList(),
    val isEmptyListNotificationVisible: Boolean = false
)

data class WardrobeViewEntity(
    val symbol: String,
    val description: String
) {
    val isAddDescriptionOptionVisible: Boolean = description.isEmpty()
}