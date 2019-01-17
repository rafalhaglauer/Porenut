package com.wardrobes.porenut.ui.element.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.extension.*
import kotlinx.android.synthetic.main.fragment_element_dashboard.*

private const val KEY_ELEMENT_ID = "key-element-id"

class ElementDashboardFragment : Fragment() {
    private lateinit var viewModel: ElementDashboardViewModel
    private val elementId: Long?
        get() = arguments?.getLong(KEY_ELEMENT_ID)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        container?.inflate(R.layout.fragment_element_dashboard)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModel()
        observeViewModel()
        setupListeners()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this)[ElementDashboardViewModel::class.java]
    }

    private fun observeViewModel() {
        with(viewModel) {
            loadingState.observe(viewLifecycleOwner) {
                progress isVisibleWhen this
                layoutContent isVisibleWhen not()
            }
            messageEvent.observeEvent(viewLifecycleOwner) {
                showMessage(it)
            }
            navigateUpEvent.observeEvent(viewLifecycleOwner) {
                navigateUp()
            }
        }
    }

    private fun setupListeners() {
        btnElementDetails.setOnClickListener {
//            wardrobeId?.also { id ->
//                navigateTo(R.id.wardrobeDashboardFragmentToWardrobeDetails, WardrobeDetailsFragment.createExtras(id))
//            }
        }
        btnDrillings.setOnClickListener {
//            wardrobeId?.also { id ->
//                navigateTo(R.id.wardrobeDashboardFragmentToElementGroupFragment, ElementGroupFragment.createExtras(id))
//            }
        }
        btnDrillingSets.setOnClickListener {
//            wardrobeId?.also { id ->
//                navigateTo(R.id.wardrobeDashboardFragmentToWardrobeGallery, WardrobeGalleryFragment.createExtras(id))
//            }
        }
        btnRemoveElement.setOnClickListener {
            elementId?.also { viewModel.deleteElement(it) }
        }
    }

    companion object {

        fun createExtras(elementId: Long): Bundle = Bundle().apply {
            putLong(KEY_ELEMENT_ID, elementId)
        }
    }
}