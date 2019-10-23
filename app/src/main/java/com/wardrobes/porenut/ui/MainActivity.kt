package com.wardrobes.porenut.ui

import android.Manifest
import android.os.Bundle
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "DSG"

        // TODO Configure Toolbar
        bottomNavigation.setupWithNavController(mainNavController)
        requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0) // TODO Proper manage of permissions
    }

    override fun onBackPressed() {
        findNavController(R.id.navHostMain).navigateUp()
    }
}