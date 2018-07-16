package com.wardrobes.porenut.ui.wardrobe.group.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import com.wardrobes.porenut.domain.Wardrobe
import com.wardrobes.porenut.ui.base.TabActivity
import com.wardrobes.porenut.ui.extension.launchActvity
import com.wardrobes.porenut.ui.wardrobe.group.model.WardrobeGroupViewModel
import com.wardrobes.porenut.ui.wardrobe.manage.ManageWardrobeActivity
import kotlinx.android.synthetic.main.activity_tab_list.*

class WardrobeGroupActivity : TabActivity() {
    private lateinit var wardrobeGroupViewModel: WardrobeGroupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fab.setOnClickListener { showManageWardrobeDialog() }
        wardrobeGroupViewModel = ViewModelProviders.of(this)[WardrobeGroupViewModel::class.java]
    }

    override fun getFragments(): Map<String, Fragment> =
            mapOf(
                    "TYPOWE" to WardrobeGroupFragment(),
                    "NIETYPOWE" to WardrobeGroupFragment()
            )

    override fun onTabChangedListener(position: Int) {
        wardrobeGroupViewModel.creationType = if (position == 0) Wardrobe.CreationType.STANDARD else Wardrobe.CreationType.CUSTOM
    }

    private fun showManageWardrobeDialog() {
        launchActvity<ManageWardrobeActivity>()
    }
}