package com.wardrobes.porenut.ui.element.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.drilling.standard.group.DrillingGroupFragment
import com.wardrobes.porenut.ui.element.composition.group.ElementCompositionGroupFragment
import com.wardrobes.porenut.ui.element.detail.ElementDetailsFragment
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
            elementId?.also { id ->
                navigateTo(R.id.elementDashboardFragmentToElementDetails, ElementDetailsFragment.createExtras(id))
            }
        }
        btnDrillings.setOnClickListener {
            elementId?.also { id ->
                navigateTo(R.id.elementDashboardFragmentToDrillingGroup, DrillingGroupFragment.createExtras(id))
            }
        }
        btnDrillingSets.setOnClickListener {
            elementId?.also { id ->
                navigateTo(R.id.elementDashboardFragmentToElementCompositionGroup, ElementCompositionGroupFragment.createExtras(id))
            }
        }
        btnRemoveElement.setOnClickListener {
            elementId?.also { id ->
                viewModel.deleteElement(id)
            }
        }
    }

    companion object {

        fun createExtras(elementId: Long): Bundle = Bundle().apply {
            putLong(KEY_ELEMENT_ID, elementId)
        }
    }
}