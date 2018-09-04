//package com.wardrobes.porenut.ui.element
//
//import android.os.Bundle
//import androidx.lifecycle.ViewModelProviders
//import com.wardrobes.porenut.R
//
//class ElementDetailsActivity : TabActivity() {
//    private lateinit var elementDetailsViewModel: ElementDetailsViewModel
//    private lateinit var drillingGroupViewModel: DrillingGroupViewModel
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        elementDetailsViewModel = ViewModelProviders.of(this)[ElementDetailsViewModel::class.java]
//        elementDetailsViewModel.elementId = intent.elementId
//        elementDetailsViewModel.creationType = intent.wardrobeCreationType
//        drillingGroupViewModel = ViewModelProviders.of(this)[DrillingGroupViewModel::class.java]
//        drillingGroupViewModel.wardrobeId = intent.wardrobeId
//        drillingGroupViewModel.elementId = intent.elementId
//        drillingGroupViewModel.creationType = intent.wardrobeCreationType
//    }
//
//    override fun getFragments(): List<TabFragment> {
//        val elementDetailsFragment = ElementDetailsFragment().apply {
//            pageTitle = applicationContext.getString(R.string.l_details)
//        }
//        val drillingGroupFragment = DrillingGroupFragment().apply {
//            elementId = intent.elementId
//        }
//        return listOf(elementDetailsFragment, drillingGroupFragment)
//    }
//}