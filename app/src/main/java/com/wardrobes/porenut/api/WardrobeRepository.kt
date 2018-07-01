package com.wardrobes.porenut.api

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.wardrobes.porenut.model.Wardrobe
import com.wardrobes.porenut.model.WardrobeType
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

class WardrobeRepository {
    private val wardrobeInterface = BaseProvider.retrofit.create(WardrobeInterface::class.java)

    private val wardrobes: MutableList<Wardrobe> = mutableListOf()

    fun getAll(): LiveData<List<Wardrobe>> =
            MutableLiveData<List<Wardrobe>>().apply {
                Flowable.interval(1000, 2000, TimeUnit.MILLISECONDS)
                        .applySchedulers()
                        .subscribe {
                            wardrobes.add(Wardrobe("DSG", 600f, 700f, 520f, WardrobeType.HANGING))
                            postValue(wardrobes)
                        }

//                wardrobeInterface.getAll()
//                        .fetchStateFullModel(
//                                onLoading = { },
//                                onSuccess = { value = it },
//                                onError = { it.printStackTrace() }
//                        )
            }
}