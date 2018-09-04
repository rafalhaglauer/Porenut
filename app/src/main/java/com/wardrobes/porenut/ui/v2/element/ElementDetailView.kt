package com.wardrobes.porenut.ui.v2.element

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.extension.inflate
import com.wardrobes.porenut.ui.extension.setVisible
import kotlinx.android.synthetic.main.view_element_detail.view.*

class ElementDetailView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    var onDelete: (() -> Unit)? = null
    var onCopy: (() -> Unit)? = null
    var onEdit: (() -> Unit)? = null

    init {
        inflate(R.layout.view_element_detail, true)
        setupActionButton()
    }

    private fun setupActionButton() {
        btnActionElementDetail.inflate(R.menu.menu_element_detail)
        btnActionElementDetail.setOnActionSelectedListener {
            when (it.id) {
                R.id.action_delete_element -> onDelete?.invoke()
                R.id.action_copy_element -> onCopy?.invoke()
                R.id.action_edit_element -> onEdit?.invoke()
            }
            false
        }
    }

    fun bind(viewEntity: ElementViewEntity) {
        with(viewEntity) {
            txtElementName.text = name
            txtElementLength.text = length
            txtElementWidth.text = width
            txtElementHeight.text = height
        }
    }

    fun showProgress(shouldBeVisible: Boolean) {
        progressElementDetail.setVisible(shouldBeVisible)
    }

    fun showContent(shouldBeVisible: Boolean) {
        contentElementDetail.setVisible(shouldBeVisible)
    }

    fun showActionButton(shouldBeVisible: Boolean) {
        btnActionElementDetail.setVisible(shouldBeVisible)
    }
}