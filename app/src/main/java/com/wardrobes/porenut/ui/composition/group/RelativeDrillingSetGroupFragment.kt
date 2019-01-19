package com.wardrobes.porenut.ui.composition.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.wardrobes.porenut.R
import com.wardrobes.porenut.domain.RelativeDrillingSet
import com.wardrobes.porenut.ui.composition.detail.RelativeDrillingSetDetailsFragment
import com.wardrobes.porenut.ui.common.extension.*
import kotlinx.android.synthetic.main.fragment_relative_drilling_set_group.*

class RelativeDrillingSetGroupFragment : Fragment() {
    private lateinit var viewModel: RelativeDrillingSetGroupViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        container?.inflate(R.layout.fragment_relative_drilling_set_group)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModel()
        observeViewModel()
        setupAdapter()
        setupButton()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this)[RelativeDrillingSetGroupViewModel::class.java]
    }

    // TODO Empty list view
    private fun observeViewModel() {
        viewModel.viewState
            .observe(viewLifecycleOwner) {
                progressRelativeDrillingSet.isVisibleWhen(isLoading)
                contentRelativeDrillingSet.isVisibleWhen(!isLoading)
                btnAddRelativeDrillingSet.isVisibleWhen(!isLoading)
                bind(drillingSets)
            }
        viewModel.errorMessageEvent
            .observeEvent(viewLifecycleOwner) {
                showMessage(it)
            }
        viewModel.fetchDrillingSets()
    }

    private fun setupAdapter() {
        contentRelativeDrillingSet.apply {
            layoutManager = LinearLayoutManager(context)
            setDivider(R.drawable.divider)
            adapter = RelativeDrillingSetGroupAdapter {
                navigateTo(R.id.relativeDrillingSetGroupFragmentToRelativeDrillingSetDetails, RelativeDrillingSetDetailsFragment.createExtras(it))
            }
        }
    }

    private fun setupButton() {
        btnAddRelativeDrillingSet.setOnClickListener {
            navigateTo(R.id.relativeDrillingSetGroupFragmentToCreateRelativeDrillingSet)
        }
    }

    private fun bind(drillingSets: List<RelativeDrillingSet>) {
        (contentRelativeDrillingSet.adapter as RelativeDrillingSetGroupAdapter).setItems(drillingSets)
    }
}