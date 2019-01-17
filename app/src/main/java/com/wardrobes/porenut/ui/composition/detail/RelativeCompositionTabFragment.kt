package com.wardrobes.porenut.ui.composition.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.drilling.relative.manage.ManageRelativeDrillingActivity
import com.wardrobes.porenut.ui.extension.*
import com.wardrobes.porenut.ui.vo.Result
import com.wardrobes.porenut.ui.vo.relativeCompositionId
import com.wardrobes.porenut.ui.vo.relativeDrillingId
import kotlinx.android.synthetic.main.fragment_relative_composition_tab.*

class RelativeCompositionTabFragment : Fragment() {
    private lateinit var viewModel: RelativeCompositionTabViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.fragment_relative_composition_tab)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[RelativeCompositionTabViewModel::class.java]
        setupElementSwitch()
        setupViewModel()
        setupListeners()
        viewModel.showDetails()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Result.ADDED.value) viewModel.showDrillingGroup()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun setupElementSwitch() {
        switchRelativeCompositionTab.setOnToggleSwitchChangeListener { _, _ ->
            when (switchRelativeCompositionTab.checkedTogglePosition) {
                0 -> viewModel.showDetails()
                1 -> viewModel.showDrillingGroup()
            }
        }
    }

    private fun setupViewModel() {
        viewModel.relativeCompositionId = arguments?.relativeCompositionId
        viewModel.viewState
            .observe(viewLifecycleOwner) {
                viewRelativeCompositionDetail.isVisibleWhen(areDetailsVisible)
                viewDrillingGroup.isVisibleWhen(isDrillingGroupVisible)
            }
        viewModel.messageEvent
            .observe(viewLifecycleOwner) {
                getContentIfNotHandled()?.also {
                    context?.showMessage(it)
                }
            }
        viewModel.navigateUpEvent
            .observe(viewLifecycleOwner) {
                getContentIfNotHandled()?.also {
                    navigateUp()
                }
            }
        viewModel.detailViewState
            .observe(viewLifecycleOwner) {
                viewEntity?.also { viewRelativeCompositionDetail.bind(it) }
                viewRelativeCompositionDetail.showProgress(isLoading)
                viewRelativeCompositionDetail.showContent(!isLoading)
            }
        viewModel.drillingGroupViewState
            .observe(viewLifecycleOwner) {
                viewDrillingGroup.bind(drillingNames)
                viewDrillingGroup.showProgress(isLoading)
                viewDrillingGroup.showContent(!isLoading)
                viewDrillingGroup.showEmptyListNotification(isEmptyListNotificationVisible)
            }
    }

    private fun setupListeners() {
        viewRelativeCompositionDetail.onDelete = {
            viewModel.delete()
        }
        viewRelativeCompositionDetail.onEdit = {
            // TODO
        }

        viewDrillingGroup.onSelected = {
            launchActivity<ManageRelativeDrillingActivity> {
                arguments?.relativeCompositionId?.also { relativeCompositionId = it }
                relativeDrillingId = viewModel.getDrillingId(it)
            }
        }

        viewDrillingGroup.onAdd = {
            launchActivity<ManageRelativeDrillingActivity> {
                arguments?.relativeCompositionId?.also { relativeCompositionId = it }
            }
        }
    }
}