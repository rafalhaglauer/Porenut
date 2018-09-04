package com.wardrobes.porenut.ui.extension

import android.app.Activity
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager

fun View.setVisible(shouldBeVisible: Boolean) {
    if (shouldBeVisible) show() else begone()
}

fun View.show() {
    visibility = VISIBLE
}

fun View.begone() {
    visibility = GONE
}

fun View.hideKeyboard() {
    (context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).also { it.hideSoftInputFromWindow(windowToken, 0) }
}