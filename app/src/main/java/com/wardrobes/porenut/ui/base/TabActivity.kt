package com.wardrobes.porenut.ui.base

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.wardrobes.porenut.R
import kotlinx.android.synthetic.main.activity_tab_list.*

abstract class TabActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_list)
        setSupportActionBar(toolbar)
        setupViewPager(viewPager)
        tabLayout.setupWithViewPager(viewPager)
    }

    abstract fun getFragments(): List<TabFragment>

    private fun setupViewPager(viewPager: ViewPager) {
        ViewPagerAdapter(supportFragmentManager).apply {
            getFragments().forEach { addFragment(it) }
        }.also {
            viewPager.adapter = it
        }
    }

    internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val fragmentList = mutableListOf<TabFragment>()

        override fun getItem(position: Int) = fragmentList[position]

        override fun getCount(): Int = fragmentList.size

        override fun getPageTitle(position: Int): String = fragmentList[position].getTitle()

        fun addFragment(fragment: TabFragment) {
            fragmentList.add(fragment)
        }
    }
}