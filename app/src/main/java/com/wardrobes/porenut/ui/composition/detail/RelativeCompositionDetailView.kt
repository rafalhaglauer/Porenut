package com.wardrobes.porenut.ui.composition.detail

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.extension.inflate
import com.wardrobes.porenut.ui.extension.isVisibleWhen
import kotlinx.android.synthetic.main.view_relative_composition_detail.view.*

class RelativeCompositionDetailView(context: Context, attrs: AttributeSet?) :
    FrameLayout(context, attrs) {
    var onDelete: (() -> Unit)? = null
    var onEdit: (() -> Unit)? = null

    init {
        inflate(R.layout.view_relative_composition_detail, true)
        setupActionButton()
    }

    private fun setupActionButton() {
        btnActionRelativeCompositionDetail.inflate(R.menu.menu_relative_composition_detail)
        btnActionRelativeCompositionDetail.setOnActionSelectedListener {
            when (it.id) {
                R.id.action_delete_relative_composition -> onDelete?.invoke()
                R.id.action_edit_relative_composition -> onEdit?.invoke()
            }
            false
        }
    }

    fun bind(viewEntity: RelativeCompositionDetailViewEntity) {
        with(viewEntity) {
            txtRelativeCompositionName.text = name
            txtXReferenceLength.text = xReferenceLength
            txtYReferenceLength.text = yReferenceLength
        }
    }

    fun showProgress(shouldBeVisible: Boolean) {
        progressRelativeCompositionDetail.isVisibleWhen(shouldBeVisible)
    }

    fun showContent(shouldBeVisible: Boolean) {
        contentRelativeCompositionDetail.isVisibleWhen(shouldBeVisible)
    }
}