package com.wardrobes.porenut.ui.pattern.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.common.extension.*
import kotlinx.android.synthetic.main.fragment_manage_wardrobe_pattern.*

private const val KEY_WARDROBE_PATTERN_ID = "key-wardrobe-pattern-id"

class ManageWardrobePatternFragment : Fragment() {

    private val viewModel: ManageWardrobePatternViewModel by injectViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_manage_wardrobe_pattern)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewModel()
        setupListeners()
        unpackArguments()
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
                btnManageWardrobePattern.text = getString(btnActionText)
                showMessage(errorMessage)
                bind(viewEntity)
            }
    }

    private fun observeNavigationEvent() {
        viewModel.navigateBack
            .observeEvent(viewLifecycleOwner) {
                navigateUp()
            }
    }

    private fun setupListeners() {
        btnManageWardrobePattern.setOnClickListener {
            it.hideKeyboard()
            viewModel.manageWardrobe(build())
        }
    }

    private fun unpackArguments() {
        arguments?.run {
            viewModel.patternId = getString(KEY_WARDROBE_PATTERN_ID).orEmpty()
        }
    }

    private fun bind(viewEntity: ManageWardrobePatternViewEntity) {
        with(viewEntity) {
            editWardrobePatternSymbol.setText(symbol)
            editWardrobePatternDescription.setText(description)
        }
    }

    private fun build(): ManageWardrobePatternViewEntity = ManageWardrobePatternViewEntity(
        symbol = editWardrobePatternSymbol.string(),
        description = editWardrobePatternDescription.string()
    )

    companion object {

        fun createExtras(wardrobePatternId: String): Bundle = Bundle().apply {
            putString(KEY_WARDROBE_PATTERN_ID, wardrobePatternId)
        }
    }
}