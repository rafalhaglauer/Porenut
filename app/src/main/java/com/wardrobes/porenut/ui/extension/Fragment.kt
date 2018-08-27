package com.wardrobes.porenut.ui.extension

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment

inline fun <reified T : Activity> Fragment.launchActivity(
    requestCode: Int = 0,
    noinline applyExtras: Intent.() -> Unit = { }
) {
    startActivityForResult(Intent(activity, T::class.java).apply {
        applyExtras()
    }, requestCode)
}