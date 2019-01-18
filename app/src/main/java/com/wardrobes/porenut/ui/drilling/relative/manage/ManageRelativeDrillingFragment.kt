package com.wardrobes.porenut.ui.drilling.relative.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.extension.*
import kotlinx.android.synthetic.main.fragment_manage_relative_drilling.*

private const val KEY_DRILLING_ID = "key-drilling-id"
private const val KEY_DRILLING_SET_ID = "key-drilling-set-id"

class ManageRelativeDrillingFragment : Fragment() {
    private lateinit var viewModel: ManageRelativeDrillingViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        container?.inflate(R.layout.fragment_manage_relative_drilling)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModel()
        observeViewModel()
        setupListeners()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this)[ManageRelativeDrillingViewModel::class.java]
        arguments?.run {
            viewModel.relativeDrillingSetId = getLong(KEY_DRILLING_SET_ID).takeIf { containsKey(KEY_DRILLING_SET_ID) }
            viewModel.relativeDrillingId = getLong(KEY_DRILLING_ID).takeIf { containsKey(KEY_DRILLING_ID) }
        }
    }

    private fun observeViewModel() {
        viewModel.viewState
            .observe(viewLifecycleOwner) {
                progress isVisibleWhen isLoading
                layoutContent isVisibleWhen !isLoading
                btnDeleteRelativeDrilling isVisibleWhen isDeleteButtonVisible
                bind(viewEntity)
                txtManageRelativeDrilling.text = getString(manageText)
            }
        viewModel.errorMessageEvent
            .observeEvent(viewLifecycleOwner) {
                showMessage(it)
            }
        viewModel.navigateBackEvent
            .observeEvent(viewLifecycleOwner) {
                navigateUp()
            }
    }

    private fun setupListeners() {
        btnManageComposition.setOnClickListener {
            it.hideKeyboard()
            viewModel.manage(
                name = txtName.string(),
                xOffset = viewXOffset.offset,
                yOffset = viewYOffset.offset,
                depth = txtDepth.float(),
                diameter = txtDiameter.float()
            )
        }
        btnDeleteRelativeDrilling.setOnClickListener {
            viewModel.delete()
        }
    }

    private fun bind(viewEntity: RelativeDrillingViewEntity) {
        txtName.setText(viewEntity.name)
        txtDepth.setText(viewEntity.depth)
        txtDiameter.setText(viewEntity.diameter)
        viewXOffset.offset = viewEntity.xOffset
        viewYOffset.offset = viewEntity.yOffset
    }

    companion object {

        fun createAddExtras(drillingSetId: Long): Bundle = Bundle().apply {
            putLong(KEY_DRILLING_SET_ID, drillingSetId)
        }

        fun createManageExtras(drillingId: Long): Bundle = Bundle().apply {
            putLong(KEY_DRILLING_ID, drillingId)
        }
    }
}
