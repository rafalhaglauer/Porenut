package com.wardrobes.porenut.ui.composition.group

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.composition.manage.ManageRelativeCompositionActivity
import com.wardrobes.porenut.ui.extension.*
import com.wardrobes.porenut.ui.vo.Result
import kotlinx.android.synthetic.main.view_relative_composition_group.*

private const val ADD_RELATIVE_COMPOSITION_REQUEST_CODE = 1

class RelativeCompositionGroupFragment : Fragment() {
    private lateinit var viewModel: RelativeCompositionGroupViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.view_relative_composition_group)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[RelativeCompositionGroupViewModel::class.java]
        setupAdapter()
        observeViewModel()
        btnAddRelativeCompositionGroup.setOnClickListener {
            launchActivity<ManageRelativeCompositionActivity>(ADD_RELATIVE_COMPOSITION_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Result.ADDED.value) viewModel.refresh()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun setupAdapter() {
        contentRelativeCompositionGroup.apply {
            layoutManager = LinearLayoutManager(context)
            setDivider(R.drawable.divider)
            adapter = RelativeCompositionGroupAdapter {
                navigateTo(R.id.drillingSectionToRelativeCompositionTab) {
                    //                    relativeCompositionId = viewModel.getRelativeCompositionId(it)
//                TODO!!
                }
            }
        }
    }

    private fun observeViewModel() {
        viewModel.viewState
            .observe(this, Observer {
                it?.also {
                    progressRelativeCompositionGroup.isVisibleWhen(it.isLoading)
                    contentRelativeCompositionGroup.isVisibleWhen(!it.isLoading)
                    btnAddRelativeCompositionGroup.isVisibleWhen(!it.isLoading)
                    bind(it.compositions)
                    context?.showMessage(it.errorMessage)
                }
            })
        viewModel.startObserving()
    }

    private fun bind(compositions: List<String>) {
        (contentRelativeCompositionGroup.adapter as RelativeCompositionGroupAdapter).setItems(compositions)
    }
}