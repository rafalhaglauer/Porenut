package com.wardrobes.porenut.ui.element.composition.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.extension.*
import kotlinx.android.synthetic.main.fragment_manage_element_composition.*

private const val KEY_ELEMENT_ID = "key-element-id"
private const val KEY_COMPOSITION_ID = "key-composition-id"

class ManageElementCompositionFragment : Fragment() {
    private lateinit var viewModel: ManageElementCompositionViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        container?.inflate(R.layout.fragment_manage_element_composition)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModel()
        observeViewModel()
        setupButtons()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this)[ManageElementCompositionViewModel::class.java]
        viewModel.elementId = arguments?.let { args -> args.getLong(KEY_ELEMENT_ID).takeIf { args.containsKey(KEY_ELEMENT_ID) } }
        viewModel.compositionId = arguments?.let { args -> args.getLong(KEY_COMPOSITION_ID).takeIf { args.containsKey(KEY_COMPOSITION_ID) } }

    }

    private fun observeViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner) {
            progress isVisibleWhen isLoading
            layoutContent isVisibleWhen !isLoading
            spinnerDrillingSet.adapter = ArrayAdapter<String>(
                context,
                android.R.layout.simple_list_item_1,
                compositionNames
            ) // TODO refactor this sheet
            txtManageComposition.text = getString(buttonText)
            btnRemoveComposition isVisibleWhen isRemovePossible
            spinnerDrillingSet.isEnabled = isDrillingSetPositionEnable
            bind(viewEntity)
        }
        viewModel.errorMessageEvent.observeEvent(viewLifecycleOwner) {
            showMessage(it)
        }
        viewModel.navigateBackEvent.observeEvent(viewLifecycleOwner) {
            navigateUp()
        }
    }

    private fun setupButtons() {
        btnManageComposition.setOnClickListener {
            viewModel.manage(
                drillingCompositionName = spinnerDrillingSet.selectedItem as String,
                xOffset = viewXOffset.offset,
                yOffset = viewYOffset.offset
            )
        }
        btnRemoveComposition.setOnClickListener {
            viewModel.remove()
        }
    }

    private fun bind(viewEntity: ManageElementCompositionViewEntity) {
        with(viewEntity) {
            spinnerDrillingSet.setSelection(drillingSetPosition)
            viewXOffset.offset = xOffset
            viewYOffset.offset = yOffset
        }
    }

    companion object {

        fun createAddExtras(elementId: Long): Bundle = Bundle().apply {
            putLong(KEY_ELEMENT_ID, elementId)
        }

        fun createManageExtras(compositionId: Long): Bundle = Bundle().apply {
            putLong(KEY_COMPOSITION_ID, compositionId)
        }
    }
}