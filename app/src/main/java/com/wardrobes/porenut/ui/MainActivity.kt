package com.wardrobes.porenut.ui

import android.Manifest
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.common.extension.isVisibleWhen
import com.wardrobes.porenut.ui.navigation.BottomBarVisibilityResolver
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mainNavController = findNavController(R.id.navHostMain)
        mainNavController.addOnDestinationChangedListener { _, destination, _ ->
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(
                    destination.id != R.id.wardrobeCollectionDashboardFragment
                        && destination.id != R.id.wardrobeSectionFragment
                        && destination.id != R.id.relativeDrillingSetGroupFragment
                )
            }

            bottomNavigation isVisibleWhen BottomBarVisibilityResolver.shouldBeVisible(destination.id)
        }

        // TODO Configure Toolbar
        bottomNavigation.setupWithNavController(mainNavController)
        requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0) // TODO Proper manage of permissions
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