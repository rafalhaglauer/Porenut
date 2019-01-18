package com.wardrobes.porenut.ui.composition.manage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.relative.RelativeDrillingSetRepository
import com.wardrobes.porenut.data.relative.RelativeDrillingSetRestRepository
import com.wardrobes.porenut.domain.RelativeDrillingSet
import com.wardrobes.porenut.ui.extension.updateValue
import com.wardrobes.porenut.ui.vo.Event

class ManageRelativeDrillingSetViewModel(
    private val relativeDrillingCompositionRepository: RelativeDrillingSetRepository = RelativeDrillingSetRestRepository
) : ViewModel() {
    val loadingState: LiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    val errorMessageEvent: LiveData<Event<String>> = MutableLiveData()
    val navigateBackEvent: LiveData<Event<Unit>> = MutableLiveData()

    fun add(name: String) {
        relativeDrillingCompositionRepository.add(RelativeDrillingSet(name = name))
            .fetchStateFullModel(
                onLoading = { createLoadingState() },
                onSuccess = { createSuccessState() },
                onError = { createErrorState(it) }
            )
    }

    private fun createLoadingState() {
        loadingState.updateValue(true)
    }

    private fun createSuccessState() {
        navigateBackEvent.updateValue(Event(Unit))
    }

    private fun createErrorState(errorMessage: String) {
        errorMessageEvent.updateValue(Event(errorMessage))
        loadingState.updateValue(false)
    }
}