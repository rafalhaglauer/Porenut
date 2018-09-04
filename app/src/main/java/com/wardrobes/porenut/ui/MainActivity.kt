package com.wardrobes.porenut.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.wardrobes.porenut.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mainNavController = findNavController(R.id.navHostMain)
        mainNavController.addOnNavigatedListener { _, destination ->
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(
                    destination.id != R.id.wardrobeCollectionSectionFragment
                            && destination.id != R.id.wardrobeSectionFragment
                            && destination.id != R.id.drillingSectionFragment
                )
            }
        }
        bottomNavigation.setupWithNavController(mainNavController)
    }

    override fun onOptionsItemSelected(item: MenuItem?) =
        when (item?.itemId) {
            android.R.id.home -> onBackPressed().run { true }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onBackPressed() {
        findNavController(R.id.navHostMain).navigateUp()
    }
}