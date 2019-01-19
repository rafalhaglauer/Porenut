package com.wardrobes.porenut.ui.drilling.standard.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.common.extension.*
import kotlinx.android.synthetic.main.fragment_drilling_group.*

private const val KEY_ELEMENT_ID = "key-element-id"

class DrillingGroupFragment : Fragment() {
    private lateinit var viewModel: DrillingGroupViewModel

    private val elementId: Long?
        get() = arguments?.let { args -> args.getLong(KEY_ELEMENT_ID).takeIf { args.containsKey(KEY_ELEMENT_ID) } }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        container?.inflate(R.layout.fragment_drilling_group)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModel()
        setupAdapter()
        observeViewModel()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this)[DrillingGroupViewModel::class.java]
        viewModel.elementId = elementId
    }

    private fun observeViewModel() {
        viewModel.viewState
            .observe(viewLifecycleOwner) {
                progressDrillingGroup isVisibleWhen isLoading
                contentDrillingGroup isVisibleWhen !isLoading
                emptyListNotificationDrillingGroup isVisibleWhen isEmptyListNotificationVisible
                bind(viewEntities)
            }
        viewModel.errorEvent
            .observeEvent(viewLifecycleOwner) {
                showMessage(it)
                navigateUp()
            }
    }

    fun bind(viewEntities: List<DrillingGroupViewEntity>) {
        (contentDrillingGroup.adapter as DrillingGroupAdapter).setItems(viewEntities)
    }

    private fun setupAdapter() {
        contentDrillingGroup.apply {
            layoutManager = LinearLayoutManager(context)
            setDivider(R.drawable.divider)
            adapter = DrillingGroupAdapter()
        }
    }

    companion object {

        fun createExtras(elementId: Long): Bundle = Bundle().apply {
            putLong(KEY_ELEMENT_ID, elementId)
        }
    }
}