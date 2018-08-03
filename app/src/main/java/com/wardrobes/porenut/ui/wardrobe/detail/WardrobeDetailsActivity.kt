package com.wardrobes.porenut.ui.wardrobe.detail

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import com.wardrobes.porenut.R
import com.wardrobes.porenut.domain.UNDEFINED_ID
import com.wardrobes.porenut.ui.base.TabActivity
import com.wardrobes.porenut.ui.base.TabFragment

private const val KEY_WARDROBE_ID = "key-wardrobe-id"

class WardrobeDetailsActivity : TabActivity() {
    private lateinit var wardrobeDetailsViewModel: WardrobeDetailsViewModel
    private lateinit var elementGroupViewModel: ElementGroupViewModel

    companion object {
        private var Intent.wardrobeId: Long
            set(value) {
                putExtra(KEY_WARDROBE_ID, value)
            }
            get() = getLongExtra(KEY_WARDROBE_ID, UNDEFINED_ID)

        fun launch(activity: Activity, wardrobeId: Long = UNDEFINED_ID) {
            val intent = Intent(activity, WardrobeDetailsActivity::class.java)
            intent.wardrobeId = wardrobeId
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wardrobeDetailsViewModel = ViewModelProviders.of(this)[WardrobeDetailsViewModel::class.java]
        wardrobeDetailsViewModel.wardrobeId = intent.wardrobeId
        elementGroupViewModel = ViewModelProviders.of(this)[ElementGroupViewModel::class.java]
        elementGroupViewModel.wardrobeId = intent.wardrobeId
    }

    override fun getFragments(): List<TabFragment> {
        val wardrobeDetailsFragment = WardrobeDetailsFragment().apply {
            pageTitle = applicationContext.getString(R.string.l_details)
        }
        val elementGroupFragment = ElementGroupFragment().apply {

        }
        val wardrobeGalleryFragment = WardrobeGalleryFragment().apply {

        }
        return listOf(wardrobeDetailsFragment, elementGroupFragment, wardrobeGalleryFragment)
    }
}