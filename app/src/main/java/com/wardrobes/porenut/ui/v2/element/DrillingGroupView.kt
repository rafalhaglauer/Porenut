package com.wardrobes.porenut.ui.v2.element

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.element.DrillingGroupAdapter
import com.wardrobes.porenut.ui.extension.inflate
import com.wardrobes.porenut.ui.extension.setDivider
import com.wardrobes.porenut.ui.extension.setVisible
import kotlinx.android.synthetic.main.view_drilling_group.view.*

class DrillingGroupView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    var onAdd: (() -> Unit)? = null
    var onSelected: ((DrillingViewEntity) -> Unit)? = null

    init {
        inflate(R.layout.view_drilling_group, true)
        setupAdapter()
        setupActionButton()
    }

    fun bind(viewEntities: List<DrillingViewEntity>) {
        (contentDrillingGroup.adapter as DrillingGroupAdapter).setItems(viewEntities)
    }

    fun showProgress(shouldBeVisible: Boolean) {
        progressDrillingGroup.setVisible(shouldBeVisible)
    }

    fun showContent(shouldBeVisible: Boolean) {
        contentDrillingGroup.setVisible(shouldBeVisible)
    }

    fun showEmptyListNotification(shouldBeVisible: Boolean) {
        emptyListNotificationDrillingGroup.setVisible(shouldBeVisible)
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