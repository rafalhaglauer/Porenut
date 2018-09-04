package com.wardrobes.porenut.ui.extension

import android.app.Activity
import android.content.Intent

inline fun <reified T : Activity> Activity.launchActivity(requestCode: Int = 0, noinline applyExtras: Intent.() -> Unit = { }) {
    startActivityForResult(Intent(this, T::class.java).apply {
        applyExtras()
    }, requestCode)
}

inline fun Activity.finishWithResult(result: Int, applyExtras: Intent.() -> Unit = { }) {
    setResult(result, Intent().apply { applyExtras() })
    finish()
}