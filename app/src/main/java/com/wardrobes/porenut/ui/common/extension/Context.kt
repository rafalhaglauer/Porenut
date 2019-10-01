package com.wardrobes.porenut.ui.common.extension

import android.content.Context
import android.widget.Toast

fun Context.showMessage(message: String?) {
    if (message?.isNotEmpty() == true) Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}