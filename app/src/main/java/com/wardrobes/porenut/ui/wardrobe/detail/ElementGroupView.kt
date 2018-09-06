package com.wardrobes.porenut.ui.wardrobe.detail

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.element.detail.ElementViewEntity
import com.wardrobes.porenut.ui.extension.inflate
import com.wardrobes.porenut.ui.extension.setDivider
import com.wardrobes.porenut.ui.extension.setVisible
import kotlinx.android.synthetic.main.view_element_group.view.*

class ElementGroupView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    var onAdd: (() -> Unit)? = null
    var onSelected: ((ElementViewEntity) -> Unit)? = null

    init {
        inflate(R.layout.view_element_group, true)
        setupAdapter()
        setupActionButton()
    }

    fun bind(viewEntities: List<ElementViewEntity>) {
        (contentElementGroup.adapter as ElementGroupAdapter).setItems(viewEntities)
    }

    fun showProgress(shouldBeVisible: Boolean) {
        progressElementGroup.setVisible(shouldBeVisible)
    }

    fun showContent(shouldBeVisible: Boolean) {
        contentElementGroup.setVisible(shouldBeVisible)
    }

    fun showEmptyListNotification(shouldBeVisible: Boolean) {
        emptyListNotificationElementGroup.setVisible(shouldBeVisible)
    }

    fun showActionButton(shouldBeVisible: Boolean) {
        btnActionElementGroup.setVisible(shouldBeVisible)
    }

    private fun setupAdapter() {
        contentElementGroup.apply {
            layoutManager = LinearLayoutManager(context)
            setDivider(R.drawable.divider)
            adapter = ElementGroupAdapter {
                onSelected?.invoke(it)
            }
        }
    }

    private fun setupActionButton() {
        btnActionElementGroup.setOnClickListener {
            onAdd?.invoke()
        }
    }
}