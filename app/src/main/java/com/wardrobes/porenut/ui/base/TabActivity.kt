package com.wardrobes.porenut.ui.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.wardrobes.porenut.R
import kotlinx.android.synthetic.main.activity_tab.*

abstract class TabActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupViewPager(viewPager)
        tabLayout.setupWithViewPager(viewPager)
    }

    abstract fun getFragments(): List<TabFragment>

    override fun onOptionsItemSelected(item: MenuItem?) =
        when (item?.itemId) {
            android.R.id.home -> onBackPressed().run { true }
            else -> super.onOptionsItemSelected(item)
        }

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