package com.wardrobes.porenut.ui.pattern.drilling.manage

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.common.extension.*
import kotlinx.android.synthetic.main.fragment_manage_drilling_pattern.*

private const val KEY_DRILLING_PATTERN_ID = "key-drilling-pattern-id"
private const val KEY_ELEMENT_PATTERN_ID = "key-element-pattern-id"

class ManageDrillingPatternFragment : Fragment() {

    private val viewModel: ManageDrillingPatternViewModel by injectViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_manage_drilling_pattern)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewModel()
        setupListeners()
        unpackArguments()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_manage_drilling_pattern, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.deleteDrillingPattern) {
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
                btnManageDrillingPattern isVisibleWhen !isLoading
                btnManageDrillingPattern.text = getString(btnActionText)
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
        btnManageDrillingPattern.setOnClickListener {
            it.hideKeyboard()
            viewModel.manageElement(build())
        }
    }

    private fun unpackArguments() {
        arguments?.run {
            viewModel.patternId = getString(KEY_DRILLING_PATTERN_ID).orEmpty()
            viewModel.elementPatternId = getString(KEY_ELEMENT_PATTERN_ID).orEmpty()
            setHasOptionsMenu(viewModel.patternId.isNotEmpty())
        }
    }

    private fun bind(viewEntity: ManageDrillingPatternViewEntity) {
        with(viewEntity) {
            editDrillingPatternName.setText(name)
            editDrillingPatternPositionX.setText(positionX)
            editDrillingPatternPositionY.setText(positionY)
        }
    }

    private fun build(): ManageDrillingPatternViewEntity = ManageDrillingPatternViewEntity(
        name = editDrillingPatternName.string(),
        positionX = editDrillingPatternPositionX.string(),
        positionY = editDrillingPatternPositionY.string()
    )

    companion object {

        fun createManageExtras(drillingPatternId: String): Bundle = Bundle().apply {
            putString(KEY_DRILLING_PATTERN_ID, drillingPatternId)
        }

        fun createNewExtras(elementPatternId: String): Bundle = Bundle().apply {
            putString(KEY_ELEMENT_PATTERN_ID, elementPatternId)
        }
    }
}