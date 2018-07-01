package com.wardrobes.porenut.ui.wardrobe.group.view

import android.os.Bundle
import android.support.v4.app.Fragment
import com.wardrobes.porenut.ui.base.TabActivity
import com.wardrobes.porenut.ui.wardrobe.manage.ManageWardrobeDialog
import kotlinx.android.synthetic.main.activity_tab_list.*

class WardrobeGroupActivity : TabActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fab.setOnClickListener { showManageWardrobeDialog() }
    }

    override fun getFragments(): Map<String, Fragment> =
            mapOf(
                    "TYPOWE" to WardrobeGroupFragment(),
                    "NIETYPOWE" to WardrobeGroupFragment()
            )

    private fun showManageWardrobeDialog() {
        fragmentManager.apply {
            beginTransaction().also {
                findFragmentByTag("dialog")?.apply { it.remove(this) }
                it.addToBackStack(null)
                ManageWardrobeDialog().show(it, "dialog")
            }
        }
    }
}