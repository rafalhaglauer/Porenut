package com.wardrobes.porenut.ui.wardrobe.group.model

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.wardrobes.porenut.api.WardrobeRepository
import com.wardrobes.porenut.model.Wardrobe

class WardrobeGroupViewModel(application: Application) : AndroidViewModel(application) {
    val wardrobeGroup: LiveData<List<Wardrobe>>

    private val repository: WardrobeRepository = WardrobeRepository()

    init {
        wardrobeGroup = repository.getAll()
    }
}
