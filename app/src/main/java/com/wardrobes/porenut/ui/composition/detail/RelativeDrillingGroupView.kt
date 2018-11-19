package com.wardrobes.porenut.ui.composition.detail

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.extension.inflate
import com.wardrobes.porenut.ui.extension.setDivider
import com.wardrobes.porenut.ui.extension.setVisible
import kotlinx.android.synthetic.main.view_relative_drilling_group.view.*

class RelativeDrillingGroupView(context: Context, attrs: AttributeSet?) :
    FrameLayout(context, attrs) {
    var onAdd: (() -> Unit)? = null
    var onSelected: ((String) -> Unit)? = null

    init {
        inflate(R.layout.view_relative_drilling_group, true)
        setupAdapter()
        setupActionButton()
    }

    fun bind(drillingNames: List<String>) {
        (contentRelativeDrillingGroup.adapter as RelativeDrillingGroupAdapter).setItems(
            drillingNames
        )
    }

    fun showProgress(shouldBeVisible: Boolean) {
        progressRelativeDrillingGroup.setVisible(shouldBeVisible)
    }

    fun showContent(shouldBeVisible: Boolean) {
        contentRelativeDrillingGroup.setVisible(shouldBeVisible)
    }

    fun showEmptyListNotification(shouldBeVisible: Boolean) {
        emptyListNotificationRelativeDrillingGroup.setVisible(shouldBeVisible)
    }

    private fun setupAdapter() {
        contentRelativeDrillingGroup.apply {
            layoutManager = LinearLayoutManager(context)
            setDivider(R.drawable.divider)
            adapter = RelativeDrillingGroupAdapter {
                onSelected?.invoke(it)
            }
        }
    }

    private fun setupActionButton() {
        btnAddRelativeDrillingGroup.setOnClickListener {
            onAdd?.invoke()
        }
    }
}