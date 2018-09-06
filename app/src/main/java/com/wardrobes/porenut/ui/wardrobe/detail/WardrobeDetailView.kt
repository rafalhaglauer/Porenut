package com.wardrobes.porenut.ui.wardrobe.detail

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.extension.inflate
import com.wardrobes.porenut.ui.extension.setVisible
import kotlinx.android.synthetic.main.view_wardrobe_detail.view.*

class WardrobeDetailView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    var onDelete: (() -> Unit)? = null
    var onCopy: (() -> Unit)? = null
    var onEdit: (() -> Unit)? = null

    init {
        inflate(R.layout.view_wardrobe_detail, true)
        setupActionButton()
    }

    fun bind(viewEntity: WardrobeDetailViewEntity) {
        with(viewEntity) {
            txtWardrobeSymbol.text = symbol
            txtWardrobeWidth.text = width
            txtWardrobeHeight.text = height
            txtWardrobeDepth.text = depth
            txtWardrobeType.text = context.getString(type)
        }
    }

    fun showProgress(shouldBeVisible: Boolean) {
        progressWardrobeDetail.setVisible(shouldBeVisible)
    }

    fun showContent(shouldBeVisible: Boolean) {
        contentWardrobeDetail.setVisible(shouldBeVisible)
    }

    private fun setupActionButton() {
        btnActionWardrobeDetail.inflate(R.menu.menu_wardrobe_details)
        btnActionWardrobeDetail.setOnActionSelectedListener {
            when (it.id) {
                R.id.action_delete_wardrobe -> onDelete?.invoke()
                R.id.action_copy_wardrobe -> onCopy?.invoke()
                R.id.action_edit_wardrobe -> onEdit?.invoke()
            }
            false
        }
    }
}