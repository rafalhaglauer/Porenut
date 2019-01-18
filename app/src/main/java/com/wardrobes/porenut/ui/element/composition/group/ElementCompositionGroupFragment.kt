package com.wardrobes.porenut.ui.element.composition.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.extension.*
import kotlinx.android.synthetic.main.fragment_element_composition_group.*

private const val KEY_ELEMENT_ID = "key-element-id"

class ElementCompositionGroupFragment : Fragment() {
    private lateinit var viewModel: ElementCompositionGroupViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        container?.inflate(R.layout.fragment_element_composition_group)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModel()
        setupAdapter()
        setupActionButton()
        observeViewModel()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this)[ElementCompositionGroupViewModel::class.java]
        viewModel.elementId = arguments?.let { args -> args.getLong(KEY_ELEMENT_ID).takeIf { args.containsKey(KEY_ELEMENT_ID) } }
    }

    private fun observeViewModel() {
        viewModel.viewState
            .observe(viewLifecycleOwner) {
                progressElementCompositionGroup isVisibleWhen isLoading
                contentElementCompositionGroup isVisibleWhen !isLoading
                emptyListNotificationElementCompositionGroup isVisibleWhen isEmptyListNotificationVisible
                bind(names)
            }
        viewModel.errorEvent
            .observeEvent(viewLifecycleOwner) {
                showMessage(it)
            }
    }

    fun bind(compositionNames: List<String>) {
        (contentElementCompositionGroup.adapter as ElementCompositionGroupAdapter).setItems(compositionNames)
    }

    private fun setupAdapter() {
        contentElementCompositionGroup.apply {
            layoutManager = LinearLayoutManager(context)
            setDivider(R.drawable.divider)
            adapter = ElementCompositionGroupAdapter {
                // launchActivity<ManageCompositionActivity>(REQUEST_MANAGE_COMPOSITION) {
                //      arguments?.elementId?.also { elementId = it }
                // }
            }
        }
    }

    private fun setupActionButton() {
        btnAddElementComposition.setOnClickListener {
            //            launchActivity<ManageCompositionActivity>(REQUEST_MANAGE_COMPOSITION) {
//                arguments?.elementId?.also { elementId = it }
//                arguments?.wardrobeId?.also { wardrobeId = it }
//            }
        }
    }

    companion object {

        fun createExtras(elementId: Long): Bundle = Bundle().apply {
            putLong(KEY_ELEMENT_ID, elementId)
        }
    }
}