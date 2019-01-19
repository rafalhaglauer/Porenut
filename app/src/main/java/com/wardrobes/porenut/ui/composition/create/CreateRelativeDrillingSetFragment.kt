package com.wardrobes.porenut.ui.composition.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.common.extension.*
import kotlinx.android.synthetic.main.fragment_create_relative_drilling_set.*

class CreateRelativeDrillingSetFragment : Fragment() {
    private lateinit var viewModel: CreateRelativeDrillingSetViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        container?.inflate(R.layout.fragment_create_relative_drilling_set)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModel()
        observeViewModel()
        setupButton()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this)[CreateRelativeDrillingSetViewModel::class.java]
    }

    private fun observeViewModel() {
        viewModel.loadingState
            .observe(viewLifecycleOwner) {
                progress isVisibleWhen this
                layoutContent isVisibleWhen this.not()
            }
        viewModel.errorMessageEvent
            .observeEvent(viewLifecycleOwner) {
                showMessage(it)
            }
        viewModel.navigateBackEvent
            .observeEvent(viewLifecycleOwner) {
                navigateUp()
            }
    }

    private fun setupButton() {
        btnManageRelativeComposition.setOnClickListener {
            it.hideKeyboard()
            viewModel.add(txtRelativeCompositionName.string())
        }
    }
}
