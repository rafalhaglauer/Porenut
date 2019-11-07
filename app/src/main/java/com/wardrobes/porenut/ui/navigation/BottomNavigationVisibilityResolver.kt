package com.wardrobes.porenut.ui.navigation

import com.wardrobes.porenut.R

object BottomNavigationVisibilityResolver {

    private val popUpFragments: List<Int> = listOf(
        R.id.manageWardrobePatternFragment
    )

    fun shouldBeVisible(fragmentId: Int): Boolean = !popUpFragments.contains(fragmentId)

}