package com.wardrobes.porenut.ui.element

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.base.TabActivity
import com.wardrobes.porenut.ui.base.TabFragment
import com.wardrobes.porenut.ui.wardrobe.manage.creationType
import com.wardrobes.porenut.ui.wardrobe.manage.wardrobeId

class ElementDetailsActivity : TabActivity() {
    private lateinit var elementDetailsViewModel: ElementDetailsViewModel
    private lateinit var drillingGroupViewModel: DrillingGroupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        elementDetailsViewModel = ViewModelProviders.of(this)[ElementDetailsViewModel::class.java]
        elementDetailsViewModel.elementId = intent.elementId
        elementDetailsViewModel.creationType = intent.creationType
        drillingGroupViewModel = ViewModelProviders.of(this)[DrillingGroupViewModel::class.java]
        drillingGroupViewModel.wardrobeId = intent.wardrobeId
        drillingGroupViewModel.elementId = intent.elementId
        drillingGroupViewModel.creationType = intent.creationType
    }

    override fun getFragments(): List<TabFragment> {
        val elementDetailsFragment = ElementDetailsFragment().apply {
            pageTitle = applicationContext.getString(R.string.l_details)
        }
        val drillingGroupFragment = DrillingGroupFragment().apply {
            elementId = intent.elementId
        }
        return listOf(elementDetailsFragment, drillingGroupFragment)
    }
}