package com.wardrobes.porenut.ui.pattern.element.manage

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.common.extension.*
import kotlinx.android.synthetic.main.fragment_manage_element_pattern.*

private const val KEY_ELEMENT_PATTERN_ID = "key-element-pattern-id"
private const val KEY_WARDROBE_PATTERN_ID = "key-wardrobe-pattern-id"

class ManageElementPatternFragment : Fragment() {

    private val viewModel: ManageElementPatternViewModel by injectViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_manage_element_pattern)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewModel()
        setupListeners()
        unpackArguments()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_manage_element_pattern, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.deleteElementPattern) {
            viewModel.deletePattern()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
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
                btnManageElementPattern isVisibleWhen !isLoading
                btnManageElementPattern.text = getString(btnActionText)
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
        btnManageElementPattern.setOnClickListener {
            it.hideKeyboard()
            viewModel.manageElement(build())
        }
    }

    private fun unpackArguments() {
        arguments?.run {
            viewModel.patternId = getString(KEY_ELEMENT_PATTERN_ID).orEmpty()
            viewModel.wardrobePatternId = getString(KEY_WARDROBE_PATTERN_ID).orEmpty()
            setHasOptionsMenu(viewModel.patternId.isNotEmpty())
        }
    }

    private fun bind(viewEntity: ManageElementPatternViewEntity) {
        with(viewEntity) {
            editElementPatternName.setText(name)
            editElementPatternLength.setText(lengthPattern)
            editElementPatternWidth.setText(widthPattern)
            editElementPatternHeight.setText(height)
        }
    }

    private fun build(): ManageElementPatternViewEntity = ManageElementPatternViewEntity(
        name = editElementPatternName.string(),
        lengthPattern = editElementPatternLength.string(),
        widthPattern = editElementPatternWidth.string(),
        height = editElementPatternHeight.string()
    )

    companion object {

        fun createManageExtras(elementPatternId: String): Bundle = Bundle().apply {
            putString(KEY_ELEMENT_PATTERN_ID, elementPatternId)
        }

        fun createNewExtras(wardrobePatternId: String): Bundle = Bundle().apply {
            putString(KEY_WARDROBE_PATTERN_ID, wardrobePatternId)
        }
    }
}