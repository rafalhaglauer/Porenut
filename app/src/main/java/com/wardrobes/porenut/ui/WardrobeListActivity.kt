package com.wardrobes.porenut.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import com.wardrobes.porenut.R
import kotlinx.android.synthetic.main.activity_tab_list.*

class WardrobeListActivity : TabActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fab.setOnClickListener { showManageWardrobeDialog() }
    }

    override fun getLayoutRes(): Int = R.layout.activity_tab_list

    override fun getFragments(): Map<String, Fragment> =
            mapOf(
                    "TYPOWE" to ListFragment(),
                    "NIETYPOWE" to ListFragment()
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