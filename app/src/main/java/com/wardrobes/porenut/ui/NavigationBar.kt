package com.wardrobes.porenut.ui

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.widget.FrameLayout
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.extension.inflate
import com.wardrobes.porenut.ui.relative.composition.RelativeCompositionGroupActivity
import com.wardrobes.porenut.ui.wardrobe.group.WardrobeGroupActivity
import kotlinx.android.synthetic.main.view_navigation_bar.view.*

class NavigationBar(context: Context?, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    init {
        inflate(R.layout.view_navigation_bar, true)
        btnWardrobes.setOnClickListener {
            context?.also {
                it.startActivity(Intent(context, WardrobeGroupActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                })
            }
        }

        btnDrillingSets.setOnClickListener {
            context?.also {
                it.startActivity(
                    Intent(
                        context,
                        RelativeCompositionGroupActivity::class.java
                    ).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    })
            }
        }
    }
}