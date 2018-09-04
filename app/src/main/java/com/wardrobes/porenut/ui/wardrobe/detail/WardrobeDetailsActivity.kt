//package com.wardrobes.porenut.ui.wardrobe.detail
//
//import android.os.Bundle
//import androidx.lifecycle.ViewModelProviders
//import com.wardrobes.porenut.R
//
//class WardrobeDetailsActivity : TabActivity() {
//    private lateinit var wardrobeDetailsViewModel: WardrobeDetailsViewModel
//    private lateinit var elementGroupViewModel: ElementGroupViewModel
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        wardrobeDetailsViewModel = ViewModelProviders.of(this)[WardrobeDetailsViewModel::class.java]
//        wardrobeDetailsViewModel.wardrobeId = intent.wardrobeId
//        elementGroupViewModel = ViewModelProviders.of(this)[ElementGroupViewModel::class.java]
//        elementGroupViewModel.creationType = intent.wardrobeCreationType
//        elementGroupViewModel.wardrobeId = intent.wardrobeId
//    }
//
//    override fun getFragments(): List<TabFragment> {
//        val wardrobeDetailsFragment = WardrobeDetailsFragment().apply {
//            pageTitle = applicationContext.getString(R.string.l_details)
//        }
//        val elementGroupFragment = ElementGroupFragment().apply {
//
//        }
//        val wardrobeGalleryFragment = WardrobeGalleryFragment().apply {
//
//        }
//        return listOf(wardrobeDetailsFragment, elementGroupFragment, wardrobeGalleryFragment)
//    }
//}