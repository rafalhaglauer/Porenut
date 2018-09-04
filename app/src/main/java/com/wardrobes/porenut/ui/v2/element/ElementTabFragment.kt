package com.wardrobes.porenut.ui.v2.element

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.extension.*
import com.wardrobes.porenut.ui.vo.elementId
import kotlinx.android.synthetic.main.fragment_element_tab.*

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
        // TODO
    }

    private fun setupElementSwitch() {
        switchElementTab.setOnToggleSwitchChangeListener { _, _ ->
            when (switchElementTab.checkedTogglePosition) {
                0 -> viewModel.showDetails()
                1 -> viewModel.showDrillingGroup()
                2 -> viewModel.showRelativeDrillingGroup()
            }
        }
    }

    private fun setupViewModel() {
        viewModel.elementId = arguments?.elementId
        viewModel.viewState
            .observe(viewLifecycleOwner) {
                viewElementDetail.setVisible(areDetailsVisible)
                viewDrillingGroup.setVisible(isDrillingGroupVisible)
                viewRelativeDrillingGroup.setVisible(isRelativeDrillingGroupVisible)
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
            // TODO
        }
        viewDrillingGroup.onSelected = {
            // TODO
        }
    }
}