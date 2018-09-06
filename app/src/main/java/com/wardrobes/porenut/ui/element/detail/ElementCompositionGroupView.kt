package com.wardrobes.porenut.ui.element.detail

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.extension.inflate
import com.wardrobes.porenut.ui.extension.setDivider
import com.wardrobes.porenut.ui.extension.setVisible
import kotlinx.android.synthetic.main.view_element_composition_group.view.*

class ElementCompositionGroupView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    var onAdd: (() -> Unit)? = null
    var onSelected: ((String) -> Unit)? = null

    init {
        inflate(R.layout.view_element_composition_group, true)
        setupAdapter()
        setupActionButton()
    }

    fun bind(compositionNames: List<String>) {
        (contentElementCompositionGroup.adapter as ElementCompositionGroupAdapter).setItems(compositionNames)
    }

    fun showProgress(shouldBeVisible: Boolean) {
        progressElementCompositionGroup.setVisible(shouldBeVisible)
    }

    fun showContent(shouldBeVisible: Boolean) {
        contentElementCompositionGroup.setVisible(shouldBeVisible)
    }

    fun showEmptyListNotification(shouldBeVisible: Boolean) {
        emptyListNotificationElementCompositionGroup.setVisible(shouldBeVisible)
    }

    fun showActionButton(shouldBeVisible: Boolean) {
        btnActionElementCompositionGroup.setVisible(shouldBeVisible)
    }

    private fun setupAdapter() {
        contentElementCompositionGroup.apply {
            layoutManager = LinearLayoutManager(context)
            setDivider(R.drawable.divider)
            adapter = ElementCompositionGroupAdapter {
                onSelected?.invoke(it)
            }
        }
    }

    private fun setupActionButton() {
        btnActionElementCompositionGroup.setOnClickListener {
            onAdd?.invoke()
        }
    }
}