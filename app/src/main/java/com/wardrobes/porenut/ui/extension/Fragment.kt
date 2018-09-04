package com.wardrobes.porenut.ui.extension

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

private val Fragment.navController: NavController
    get() = findNavController()

inline fun <reified T : Activity> Fragment.launchActivity(requestCode: Int = 0, noinline applyExtras: Intent.() -> Unit = { }) {
    startActivityForResult(Intent(activity, T::class.java).apply(applyExtras), requestCode)
}

fun Fragment.navigateUp() {
    navController.navigateUp()
}

fun Fragment.navigateTo(@IdRes actionId: Int, applyExtras: Bundle.() -> Unit = { }) {
    navController.navigate(actionId, Bundle().apply(applyExtras))
}