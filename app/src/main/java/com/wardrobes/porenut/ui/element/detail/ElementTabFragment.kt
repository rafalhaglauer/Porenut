package com.wardrobes.porenut.ui.element.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.drilling.standard.ManageDrillingActivity
import com.wardrobes.porenut.ui.element.composition.manage.ManageCompositionActivity
import com.wardrobes.porenut.ui.extension.*
import com.wardrobes.porenut.ui.vo.Result
import com.wardrobes.porenut.ui.vo.drillingId
import com.wardrobes.porenut.ui.vo.elementId
import com.wardrobes.porenut.ui.vo.wardrobeId
import kotlinx.android.synthetic.main.fragment_element_tab.*

private const val REQUEST_MANAGE_DRILLING = 1
private const val REQUEST_MANAGE_COMPOSITION = 2

class ElementTabFragment : Fragment() {
    private lateinit var viewModel: ElementTabViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_element_tab)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[ElementTabViewModel::class.java]
        setupElementSwitch()
        setupViewModel()
        setupListeners()
        viewModel.showDetails()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_MANAGE_DRILLING) {
            if (resultCode == Result.ADDED.value || resultCode == Result.MODIFIED.value || resultCode == Result.DELETED.value) viewModel.showDrillingGroup()
        }
        if (requestCode == REQUEST_MANAGE_COMPOSITION) {
            if (resultCode == Result.ADDED.value || resultCode == Result.MODIFIED.value || resultCode == Result.DELETED.value) viewModel.showCompositionGroup()
        }
    }

    private fun setupElementSwitch() {
        switchElementTab.setOnToggleSwitchChangeListener { _, _ ->
            when (switchElementTab.checkedTogglePosition) {
                0 -> viewModel.showDetails()
                1 -> viewModel.showDrillingGroup()
                2 -> viewModel.showCompositionGroup()
            }
        }
    }

    private fun setupViewModel() {
        viewModel.elementId = arguments?.elementId
        viewModel.viewState
            .observe(viewLifecycleOwner) {
                viewElementDetail.setVisible(areDetailsVisible)
                viewDrillingGroup.setVisible(isDrillingGroupVisible)
                viewCompositionGroup.setVisible(isCompositionGroupVisible)
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
        viewModel.elementDetailViewState
            .observe(viewLifecycleOwner) {
                viewEntity?.also { viewElementDetail.bind(it) }
                viewElementDetail.showProgress(isLoading)
                viewElementDetail.showContent(!isLoading)
                viewElementDetail.showActionButton(isActionButtonVisible)
            }
        viewModel.drillingGroupViewState
            .observe(viewLifecycleOwner) {
                viewDrillingGroup.bind(viewEntities)
                viewDrillingGroup.showProgress(isLoading)
                viewDrillingGroup.showContent(!isLoading)
                viewDrillingGroup.showEmptyListNotification(isEmptyListNotificationVisible)
                viewDrillingGroup.showActionButton(isActionButtonVisible)
            }
        viewModel.compositionGroupViewState
            .observe(viewLifecycleOwner) {
                viewCompositionGroup.bind(names)
                viewCompositionGroup.showProgress(isLoading)
                viewCompositionGroup.showContent(!isLoading)
                viewCompositionGroup.showEmptyListNotification(isEmptyListNotificationVisible)
                viewCompositionGroup.showActionButton(isActionButtonVisible)
            }
    }

    private fun setupListeners() {
        viewElementDetail.onCopy = {
            // TODO
        }
        viewElementDetail.onDelete = {
            viewModel.deleteElement()
        }
        viewElementDetail.onEdit = {
            // TODO
        }

        viewDrillingGroup.onAdd = {
            launchActivity<ManageDrillingActivity>(REQUEST_MANAGE_DRILLING) {
                arguments?.elementId?.also { elementId = it }
            }
        }
        viewDrillingGroup.onSelected = {
            launchActivity<ManageDrillingActivity>(REQUEST_MANAGE_DRILLING) {
                arguments?.elementId?.also { elementId = it }
                drillingId = viewModel.getDrillingId(it)
            }
        }

        viewCompositionGroup.onAdd = {
            launchActivity<ManageCompositionActivity>(REQUEST_MANAGE_COMPOSITION) {
                arguments?.elementId?.also { elementId = it }
                arguments?.wardrobeId?.also { wardrobeId = it }
            }
        }
        viewCompositionGroup.onSelected = {
            // TODO
            // launchActivity<ManageCompositionActivity>(REQUEST_MANAGE_COMPOSITION) {
            //      arguments?.elementId?.also { elementId = it }
            // }
        }
    }
}