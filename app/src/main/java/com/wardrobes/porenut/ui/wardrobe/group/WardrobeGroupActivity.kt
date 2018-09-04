//package com.wardrobes.porenut.ui.wardrobe.group
//
//import android.os.Bundle
//import androidx.lifecycle.ViewModelProviders
//import com.wardrobes.porenut.R
//import com.wardrobes.porenut.domain.Wardrobe
//
//class WardrobeGroupActivity : TabActivity() {
//    private lateinit var creationTypeViewModel: CreationTypeViewModel
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        supportActionBar?.setDisplayHomeAsUpEnabled(false)
//        creationTypeViewModel = ViewModelProviders.of(this)[CreationTypeViewModel::class.java]
//    }
//
//    override fun getFragments(): List<TabFragment> {
//        val standardWardrobeFragment = WardrobeGroupFragment().apply {
//            wardrobeCreationType = Wardrobe.CreationType.STANDARD
//            pageTitle = applicationContext.getString(wardrobeCreationType.title)
//        }
//        val customWardrobeFragment = WardrobeGroupFragment().apply {
//            wardrobeCreationType = Wardrobe.CreationType.CUSTOM
//            pageTitle = applicationContext.getString(wardrobeCreationType.title)
//        }
//        return listOf(standardWardrobeFragment, customWardrobeFragment)
//    }
//
//    private val Wardrobe.CreationType.title: Int
//        get() = when (this) {
//            Wardrobe.CreationType.STANDARD -> R.string.l_standard
//            Wardrobe.CreationType.CUSTOM -> R.string.l_custom
//        }
//}