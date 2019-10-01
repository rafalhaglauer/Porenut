package com.wardrobes.porenut.ui.wardrobe.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.common.extension.*
import com.wardrobes.porenut.ui.wardrobe.manage.ManageWardrobeFragment
import kotlinx.android.synthetic.main.fragment_wardrobe_details.*

private const val KEY_WARDROBE_ID = "key-wardrobe-id"

class WardrobeDetailsFragment : Fragment() {
    private lateinit var viewModel: WardrobeDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        container?.inflate(R.layout.fragment_wardrobe_details)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModel()
        observeViewModel()
        setupActionButton()
        unpackArguments()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this)[WardrobeDetailViewModel::class.java]
    }

    private fun observeViewModel() {
        observeViewState()
        observeEvents()
    }

    private fun observeViewState() {
        viewModel.viewState
            .observe(viewLifecycleOwner) {
                progressWardrobeDetail isVisibleWhen isLoading
                contentWardrobeDetail isVisibleWhen !isLoading
                bind(viewEntity)
            }
    }

    private fun observeEvents() {
        viewModel.messageEvent
            .observeEvent(viewLifecycleOwner) {
                showMessage(it)
            }
        viewModel.navigateUpEvent
            .observeEvent(viewLifecycleOwner) {
                navigateUp()
            }
    }

    fun bind(viewEntity: WardrobeDetailViewEntity) {
        with(viewEntity) {
            txtWardrobeSymbol.text = symbol
            txtWardrobeWidth.text = width
            txtWardrobeHeight.text = height
            txtWardrobeDepth.text = depth
            txtWardrobeType.text = getString(type)
        }
    }

    private fun setupActionButton() {
        btnEditWardrobe.setOnClickListener {
            arguments?.getString(KEY_WARDROBE_ID)?.also { id ->
                navigateTo(R.id.wardrobeDetailsFragmentToManageWardrobe, ManageWardrobeFragment.createExtras(id))
            }
        }
    }

    private fun unpackArguments() {
        viewModel.wardrobeId = arguments?.getString(KEY_WARDROBE_ID)
    }

    companion object {

        fun createExtras(wardrobeId: String): Bundle = Bundle().apply {
            putString(KEY_WARDROBE_ID, wardrobeId)
        }
    }
}