package com.wardrobes.porenut.ui

import android.Manifest
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.wardrobes.porenut.R
import com.wardrobes.porenut.pdf.DefaultPdfGenerator
import com.wardrobes.porenut.ui.element.detail.ElementViewEntity
import com.wardrobes.porenut.ui.wardrobe.detail.WardrobeDetailViewEntity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mainNavController = findNavController(R.id.navHostMain)
        mainNavController.addOnDestinationChangedListener { _, destination, _ ->
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(
                    destination.id != R.id.wardrobeCollectionSectionFragment
                            && destination.id != R.id.wardrobeSectionFragment
                            && destination.id != R.id.drillingSectionFragment
                )
            }
        }
        bottomNavigation.setupWithNavController(mainNavController)
        requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)

        val testWardrobe = WardrobeDetailViewEntity(
            symbol = "DSG",
            width = "600",
            height = "716",
            depth = "320",
            type = R.string.l_upper
        )
        val el1 = ElementViewEntity(
            name = "Wieniec górny",
            length = "564",
            width = "300",
            height = "18"
        )
        val el2 = ElementViewEntity(
            name = "Wieniec dolny",
            length = "564",
            width = "300",
            height = "18"
        )
        val el3 = ElementViewEntity(
            name = "Bok lewy",
            length = "716",
            width = "320",
            height = "18"
        )
        val el4 = ElementViewEntity(
            name = "Bok prawy",
            length = "716",
            width = "320",
            height = "18"
        )
        val el5 = ElementViewEntity(
            name = "Półka",
            length = "563",
            width = "270",
            height = "18"
        )
        DefaultPdfGenerator(this).generate(testWardrobe, listOf(el1, el2, el3, el4, el5, el5, el5))
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