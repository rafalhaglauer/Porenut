package com.wardrobes.porenut.ui.wardrobe.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.wardrobes.porenut.R
import com.wardrobes.porenut.domain.CreationType
import com.wardrobes.porenut.ui.common.extension.*
import kotlinx.android.synthetic.main.fragment_manage_wardrobe.*

private const val KEY_WARDROBE_ID = "key-wardrobe-id"
private const val KEY_WARDROBE_CREATION_TYPE = "key-wardrobe-creation-type"
private const val KEY_COMPLETE_NAV_ACTION = "key_complete_nav_action"

class ManageWardrobeFragment : Fragment() {
    private lateinit var viewModel: ManageWardrobeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        container?.inflate(R.layout.fragment_manage_wardrobe)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModel()
        observeViewModel()
        setupListeners()
        unpackArguments()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this)[ManageWardrobeViewModel::class.java]
    }

    private fun observeViewModel() {
        observeViewState()
        observeNavigationEvent()
    }

    private fun observeViewState() {
        viewModel.viewState
            .observe(viewLifecycleOwner) {
                progress isVisibleWhen isLoading
                layoutContent isVisibleWhen !isLoading
                txtManageWardrobe.text = getString(btnActionText)
                showMessage(errorMessage)
                bind(viewEntity)
            }
    }

    private fun observeNavigationEvent() {
        viewModel.navigateBack
            .observeEvent(viewLifecycleOwner) {
                arguments?.run {
                    if(containsKey(KEY_COMPLETE_NAV_ACTION)) navigateTo(getInt(KEY_COMPLETE_NAV_ACTION)) else navigateUp()
                }
            }
    }

    private fun setupListeners() {
        btnManageWardrobe.setOnClickListener {
            it.hideKeyboard()
            viewModel.manageWardrobe(build())
        }
    }

    private fun unpackArguments() {
        arguments?.run {
            if (containsKey(KEY_WARDROBE_ID)) viewModel.wardrobeId = getLong(KEY_WARDROBE_ID)
            if (containsKey(KEY_WARDROBE_CREATION_TYPE)) viewModel.creationType = getSerializable(KEY_WARDROBE_CREATION_TYPE) as CreationType
        }
    }

    private fun bind(viewEntity: ManageWardrobeViewEntity) {
        with(viewEntity) {
            txtWardrobeSymbol.setText(symbol)
            txtWardrobeWidth.setText(width)
            txtWardrobeHeight.setText(height)
            txtWardrobeDepth.setText(depth)
            checkBoxIsUpperWardrobe.isChecked = isUpper
        }
    }

    private fun build(): ManageWardrobeViewEntity = ManageWardrobeViewEntity(
        symbol = txtWardrobeSymbol.string(),
        width = txtWardrobeWidth.string(),
        height = txtWardrobeHeight.string(),
        depth = txtWardrobeDepth.string(),
        isUpper = checkBoxIsUpperWardrobe.isChecked
    )

    companion object {

        fun createExtras(wardrobeId: Long): Bundle = Bundle().apply {
            putLong(KEY_WARDROBE_ID, wardrobeId)
        }

        fun createExtras(creationType: CreationType, completeNavAction: Int): Bundle = Bundle().apply {
            putSerializable(KEY_WARDROBE_CREATION_TYPE, creationType)
            putInt(KEY_COMPLETE_NAV_ACTION, completeNavAction)
        }
    }
}