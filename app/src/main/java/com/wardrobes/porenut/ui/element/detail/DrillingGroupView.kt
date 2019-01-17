package com.wardrobes.porenut.ui.element.detail

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.extension.inflate
import com.wardrobes.porenut.ui.extension.isVisibleWhen
import com.wardrobes.porenut.ui.extension.setDivider
import kotlinx.android.synthetic.main.view_drilling_group.view.*

class DrillingGroupView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    var onAdd: (() -> Unit)? = null
    var onSelected: ((DrillingGroupViewEntity) -> Unit)? = null

    init {
        inflate(R.layout.view_drilling_group, true)
        setupAdapter()
        setupActionButton()
    }

    fun bind(viewEntities: List<DrillingGroupViewEntity>) {
        (contentDrillingGroup.adapter as DrillingGroupAdapter).setItems(viewEntities)
    }

    fun showProgress(shouldBeVisible: Boolean) {
        progressDrillingGroup.isVisibleWhen(shouldBeVisible)
    }

    fun showContent(shouldBeVisible: Boolean) {
        contentDrillingGroup.isVisibleWhen(shouldBeVisible)
    }

    fun showEmptyListNotification(shouldBeVisible: Boolean) {
        emptyListNotificationDrillingGroup.isVisibleWhen(shouldBeVisible)
    }

    fun showActionButton(shouldBeVisible: Boolean) {
        btnActionDrillingGroup.isVisibleWhen(shouldBeVisible)
    }

    private fun setupAdapter() {
        contentDrillingGroup.apply {
            layoutManager = LinearLayoutManager(context)
            setDivider(R.drawable.divider)
            adapter = DrillingGroupAdapter {
                onSelected?.invoke(it)
            }
        }
    }

    private fun setupActionButton() {
        btnActionDrillingGroup.setOnClickListener {
            onAdd?.invoke()
        }
    }
}