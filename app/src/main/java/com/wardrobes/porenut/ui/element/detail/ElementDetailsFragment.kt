package com.wardrobes.porenut.ui.element.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.element.manage.ManageElementFragment
import com.wardrobes.porenut.ui.common.extension.*
import kotlinx.android.synthetic.main.fragment_element_details.*

private const val KEY_ELEMENT_ID = "key-element-id"

class ElementDetailsFragment : Fragment() {
    private lateinit var viewModel: ElementDetailsViewModel

    private val elementId: Long?
        get() = arguments?.let { args -> args.getLong(KEY_ELEMENT_ID).takeIf { args.containsKey(KEY_ELEMENT_ID) } }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        container?.inflate(R.layout.fragment_element_details)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModel()
        observeViewModel()
        setupActionButton()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this)[ElementDetailsViewModel::class.java]
        viewModel.elementId = elementId
    }

    private fun observeViewModel() {
        viewModel.viewState
            .observe(viewLifecycleOwner) {
                progressElementDetail isVisibleWhen isLoading
                contentElementDetail isVisibleWhen !isLoading
                bind(viewEntity)
            }
        viewModel.errorEvent
            .observeEvent(viewLifecycleOwner) {
                showMessage(it)
                navigateUp()
            }
    }

    private fun setupActionButton() {
        btnManageElement.setOnClickListener {
            elementId?.also { id ->
                navigateTo(R.id.elementDetailsFragmentToManageElement, ManageElementFragment.createManageExtras(id))
            }
        }
    }

    private fun bind(viewEntity: ElementViewEntity) {
        with(viewEntity) {
            txtElementName.text = name
            txtElementLength.text = length
            txtElementWidth.text = width
            txtElementHeight.text = height
        }
    }

    companion object {

        fun createExtras(elementId: Long): Bundle = Bundle().apply {
            putLong(KEY_ELEMENT_ID, elementId)
        }
    }
}