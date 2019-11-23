package com.wardrobes.porenut.ui.wardrobe.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.common.extension.*
import kotlinx.android.synthetic.main.fragment_manage_wardrobe.*

private const val KEY_WARDROBE_ID = "key-wardrobe-id"
private const val KEY_WARDROBE_PATTERN_ID = "key-wardrobe-pattern-id"

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
                btnManageWardrobe.text = getString(btnActionText)
                showMessage(errorMessage)
                bind(viewEntity)
            }
    }

    private fun observeNavigationEvent() {
        viewModel.navigateBack
            .observeEvent(viewLifecycleOwner) {
                navigateBack()
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
            if (containsKey(KEY_WARDROBE_ID)) viewModel.wardrobeId = getString(KEY_WARDROBE_ID)
            if (containsKey(KEY_WARDROBE_PATTERN_ID)) viewModel.wardrobePatternId = getString(KEY_WARDROBE_PATTERN_ID)
        }
    }

    private fun bind(viewEntity: ManageWardrobeViewEntity) {
        with(viewEntity) {
            txtWardrobeSymbol.setText(symbol)
            txtWardrobeWidth.setText(width)
            txtWardrobeHeight.setText(height)
            txtWardrobeDepth.setText(depth)
        }
    }

    private fun build(): ManageWardrobeViewEntity = ManageWardrobeViewEntity(
        symbol = txtWardrobeSymbol.string(),
        width = txtWardrobeWidth.string(),
        height = txtWardrobeHeight.string(),
        depth = txtWardrobeDepth.string()
    )

    private fun navigateBack() {
        with(findNavController()) {
            if (!popBackStack(R.id.wardrobeCreationTypeFragment, true)) navigateUp()
        }
    }

    companion object {

        fun createExtras(wardrobeId: String): Bundle = Bundle().apply {
            putString(KEY_WARDROBE_ID, wardrobeId)
        }

        fun createPatternExtras(wardrobePatternId: String): Bundle = Bundle().apply {
            putSerializable(KEY_WARDROBE_PATTERN_ID, wardrobePatternId)
        }
    }
}