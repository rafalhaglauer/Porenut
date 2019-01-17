package com.wardrobes.porenut.ui.drilling.relative.group

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.composition.detail.RelativeDrillingGroupAdapter
import com.wardrobes.porenut.ui.drilling.relative.manage.ManageRelativeDrillingActivity
import com.wardrobes.porenut.ui.extension.*
import com.wardrobes.porenut.ui.vo.Result
import com.wardrobes.porenut.ui.vo.relativeCompositionId
import kotlinx.android.synthetic.main.view_relative_drilling_group.*

class RelativeDrillingGroupFragment : Fragment() {

    private lateinit var viewModel: RelativeDrillingGroupViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.view_relative_drilling_group)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[RelativeDrillingGroupViewModel::class.java]
        setupAdapter()
        observeViewModel()
        btnAddRelativeDrillingGroup.setOnClickListener {
            launchActivity<ManageRelativeDrillingActivity> {
                arguments?.relativeCompositionId?.also { relativeCompositionId = it }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Result.ADDED.value) viewModel.refresh()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun setupAdapter() {
        contentRelativeDrillingGroup.apply {
            layoutManager = LinearLayoutManager(context)
            setDivider(R.drawable.divider)
            adapter = RelativeDrillingGroupAdapter {
                //                launchActivity<ManageRelativeDrillingActivity> {
//                    arguments?.relativeCompositionId?.also { relativeCompositionId = it }
//
//                }
                // TODO Navigate to manage drilling activity
            }
        }
    }

    private fun observeViewModel() {
        viewModel.viewState
            .observe(viewLifecycleOwner) {
                progressRelativeDrillingGroup.isVisibleWhen(isLoading)
                contentRelativeDrillingGroup.isVisibleWhen(!isLoading)
                btnAddRelativeDrillingGroup.isVisibleWhen(!isLoading)
                bind(drillingNames)
                context?.showMessage(errorMessage)
            }
        viewModel.relativeCompositionId = arguments?.relativeCompositionId
    }

    private fun bind(drillingNames: List<String>) {
        (contentRelativeDrillingGroup.adapter as RelativeDrillingGroupAdapter).setItems(drillingNames)
    }
}