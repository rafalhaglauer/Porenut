package com.wardrobes.porenut.ui.element

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import com.wardrobes.porenut.R
import com.wardrobes.porenut.domain.UNDEFINED_ID
import com.wardrobes.porenut.ui.base.TabActivity
import com.wardrobes.porenut.ui.base.TabFragment

private const val KEY_ELEMENT_ID = "key-element-id"

class ElementDetailsActivity : TabActivity() {
    private lateinit var elementDetailsViewModel: ElementDetailsViewModel
    private lateinit var drillingGroupViewModel: DrillingGroupViewModel

    companion object {
        private var Intent.elementId: Long
            set(value) {
                putExtra(KEY_ELEMENT_ID, value)
            }
            get() = getLongExtra(KEY_ELEMENT_ID, UNDEFINED_ID)

        fun launch(activity: Activity, elementId: Long = UNDEFINED_ID) {
            val intent = Intent(activity, ElementDetailsActivity::class.java)
            intent.elementId = elementId
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        elementDetailsViewModel = ViewModelProviders.of(this)[ElementDetailsViewModel::class.java]
        elementDetailsViewModel.elementId = intent.elementId
        drillingGroupViewModel = ViewModelProviders.of(this)[DrillingGroupViewModel::class.java]
        drillingGroupViewModel.elementId = intent.elementId
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