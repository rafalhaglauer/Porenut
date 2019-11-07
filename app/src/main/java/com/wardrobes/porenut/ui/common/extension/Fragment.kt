package com.wardrobes.porenut.ui.common.extension

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
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

fun Fragment.setTitle(title: String) {
    activity?.title = title
}

fun Fragment.setTitle(@StringRes titleResId: Int) {
    activity?.setTitle(titleResId)
}

inline fun <reified T : ViewModel> Fragment.injectViewModel(): Lazy<T> = lazy { ViewModelProviders.of(this)[T::class.java] }