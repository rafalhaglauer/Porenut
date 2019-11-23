package com.wardrobes.porenut.ui.navigation

import com.wardrobes.porenut.R

object BottomNavigationVisibilityResolver {

    private val popUpFragments: List<Int> = listOf(
        R.id.manageWardrobePatternFragment,
        R.id.manageElementPatternFragment,
        R.id.manageDrillingPatternFragment,
        R.id.wardrobeCreationTypeFragment,
        R.id.manageWardrobeFragment,
        R.id.selectWardrobePatternFragment
    )

    fun shouldBeVisible(fragmentId: Int): Boolean = !popUpFragments.contains(fragmentId)

}