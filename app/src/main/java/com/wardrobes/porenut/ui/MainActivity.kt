package com.wardrobes.porenut.ui

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.common.extension.isVisibleWhen
import com.wardrobes.porenut.ui.navigation.BottomNavigationVisibilityResolver
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()
        requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0) // TODO Proper manage of permissions
    }

    override fun onBackPressed() {
        findNavController(R.id.navHostMain).navigateUp()
    }

    private fun setupNavigation() {
        val mainNavController = findNavController()
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(mainNavController)
        bottomNavigation.setupWithNavController(mainNavController)
        mainNavController.addOnDestinationChangedListener { _, destination, _ ->
            bottomNavigation isVisibleWhen BottomNavigationVisibilityResolver.shouldBeVisible(destination.id)
        }
    }

    override fun onSupportNavigateUp(): Boolean = findNavController().navigateUp()

    private fun findNavController() = findNavController(R.id.navHostMain)

}