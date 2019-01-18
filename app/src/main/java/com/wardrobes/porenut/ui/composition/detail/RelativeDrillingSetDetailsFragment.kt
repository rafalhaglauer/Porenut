package com.wardrobes.porenut.ui.composition.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.drilling.relative.manage.ManageRelativeDrillingActivity
import com.wardrobes.porenut.ui.extension.*
import com.wardrobes.porenut.ui.vo.relativeCompositionId
import kotlinx.android.synthetic.main.fragment_relative_drilling_set_details.*

private const val KEY_DRILLING_SET_ID = "key-drilling-set-id"

class RelativeDrillingSetDetailsFragment : Fragment() {
    private lateinit var viewModel: RelativeDrillingSetDetailsViewModel

    private val drillingSetId: Long?
        get() = arguments?.let { args -> args.getLong(KEY_DRILLING_SET_ID).takeIf { args.containsKey(KEY_DRILLING_SET_ID) } }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        container?.inflate(R.layout.fragment_relative_drilling_set_details)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModel()
        observeViewModel()
        setupButton()
        setupAdapter()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this)[RelativeDrillingSetDetailsViewModel::class.java]
        viewModel.drillingSetId = drillingSetId
    }


    private fun observeViewModel() {
        viewModel.viewState
            .observe(viewLifecycleOwner) {
                bind(drillingNames)
                progressRelativeDrillingGroup isVisibleWhen isLoading
                contentRelativeDrillingGroup isVisibleWhen !isLoading
                emptyListNotificationRelativeDrillingGroup isVisibleWhen isEmptyListNotificationVisible
                // TODO Name as toolbar title?!
                // TODO Remove button on toolbar?
            }
        viewModel.messageEvent
            .observeEvent(viewLifecycleOwner) {
                showMessage(it)
            }
        viewModel.navigateUpEvent
            .observeEvent(viewLifecycleOwner) {
                navigateUp()
            }
    }

    private fun setupButton() {
        btnAddRelativeDrillingGroup.setOnClickListener {
            launchActivity<ManageRelativeDrillingActivity> {
                arguments?.relativeCompositionId?.also { relativeCompositionId = it }
            }
        }
    }

    private fun setupAdapter() {
        contentRelativeDrillingGroup.apply {
            layoutManager = LinearLayoutManager(context)
            setDivider(R.drawable.divider)
            adapter = RelativeDrillingGroupAdapter {
                launchActivity<ManageRelativeDrillingActivity> {
                    arguments?.relativeCompositionId?.also { relativeCompositionId = it }
//                    relativeDrillingId = viewModel.getDrillingId(it)
                }
            }
        }
    }

    fun bind(drillingNames: List<String>) {
        (contentRelativeDrillingGroup.adapter as RelativeDrillingGroupAdapter).setItems(drillingNames)
    }

    companion object {

        fun createExtras(drillingSetId: Long): Bundle = Bundle().apply {
            putLong(KEY_DRILLING_SET_ID, drillingSetId)
        }
    }
}