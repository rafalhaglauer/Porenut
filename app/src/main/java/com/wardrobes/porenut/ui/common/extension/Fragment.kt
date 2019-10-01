package com.wardrobes.porenut.ui.common.extension

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

private val Fragment.navController: NavController
    get() = findNavController()

fun Fragment.navigateUp() {
    navController.navigateUp()
}

fun Fragment.navigateTo(@IdRes actionId: Int, bundle: Bundle? = null) {
    navController.navigate(actionId, bundle)
}

fun Fragment.showMessage(message: String) {
    context?.showMessage(message)
}