package com.wardrobes.porenut.ui.element.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.element.detail.ElementViewEntity
import com.wardrobes.porenut.ui.extension.*
import kotlinx.android.synthetic.main.fragment_element_group.*

private const val KEY_WARDROBE_ID = "key-wardrobe-id"

class ElementGroupFragment : Fragment() {
    private lateinit var viewModel: ElementGroupViewModel

    private val wardrobeId: Long?
        get() = arguments?.getLong(KEY_WARDROBE_ID)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        container?.inflate(R.layout.fragment_element_group)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModel()
        observeViewModel()
        setupAdapter()
        setupActionButton()
        unpackArguments()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this)[ElementGroupViewModel::class.java]
    }

    private fun observeViewModel() {
        observeViewState()
        observeEvents()
    }

    private fun observeViewState() {
        viewModel.viewState
            .observe(viewLifecycleOwner) {
                progressElementGroup isVisibleWhen isLoading
                contentElementGroup isVisibleWhen !isLoading
                btnActionElementGroup isVisibleWhen !isLoading
                emptyListNotificationElementGroup isVisibleWhen isEmptyListNotificationVisible
                bind(viewEntities)
            }
    }

    private fun observeEvents() {
        viewModel.messageEvent
            .observeEvent(viewLifecycleOwner) {
                showMessage(it)
            }
        viewModel.navigateUpEvent
            .observeEvent(viewLifecycleOwner) {
                navigateUp()
            }
    }

    private fun bind(viewEntities: List<ElementViewEntity>) {
        (contentElementGroup.adapter as ElementGroupAdapter).setItems(viewEntities)
    }

    private fun setupAdapter() {
        contentElementGroup.apply {
            layoutManager = LinearLayoutManager(context)
            setDivider(R.drawable.divider)
            adapter = ElementGroupAdapter {
                //                navigateTo(R.id.wardrobeTabToElementTab) {
//                    elementId = viewModel.getElementId(it)
//                    arguments?.wardrobeId?.also { wardrobeId = it }
//                }
            }
        }
    }

    private fun setupActionButton() {
        btnActionElementGroup.setOnClickListener {
            //            wardrobeId?.also {
//                launchActivity<ManageElementActivity>(REQUEST_ADD_ELEMENT) {
//                    wardrobeId = it
//                    requestType = RequestType.ADD
//                }
        }
    }

    private fun unpackArguments() {
        viewModel.wardrobeId = wardrobeId
    }

    companion object {

        fun createExtras(wardrobeId: Long): Bundle = Bundle().apply {
            putLong(KEY_WARDROBE_ID, wardrobeId)
        }
    }
}