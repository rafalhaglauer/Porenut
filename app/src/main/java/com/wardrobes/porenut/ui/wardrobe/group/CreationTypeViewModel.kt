package com.wardrobes.porenut.ui.wardrobe.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wardrobes.porenut.domain.Wardrobe

class CreationTypeViewModel : ViewModel() {

    val viewState: LiveData<CreationTypeViewState> = MutableLiveData()

    fun refresh() {
        (viewState as MutableLiveData).value = CreationTypeViewState(
            shouldRefreshStandardWardrobes = true,
            shouldRefreshCustomWardrobes = true
        )
    }

    fun notifyRefreshed(creationType: Wardrobe.CreationType) {
        (viewState as MutableLiveData).value = when (creationType) {
            Wardrobe.CreationType.STANDARD -> viewState.value?.copy(shouldRefreshStandardWardrobes = false)
            Wardrobe.CreationType.CUSTOM -> viewState.value?.copy(shouldRefreshCustomWardrobes = false)
        }
    }
}

data class CreationTypeViewState(
    val shouldRefreshCustomWardrobes: Boolean = false,
    val shouldRefreshStandardWardrobes: Boolean = false
)