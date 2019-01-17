package com.wardrobes.porenut.ui.wardrobe.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.element.group.ElementGroupFragment
import com.wardrobes.porenut.ui.extension.*
import com.wardrobes.porenut.ui.wardrobe.detail.WardrobeDetailsFragment
import com.wardrobes.porenut.ui.wardrobe.gallery.WardrobeGalleryFragment
import kotlinx.android.synthetic.main.fragment_wardrobe_dashboard.*

private const val KEY_WARDROBE_ID = "key-wardrobe-id"

class WardrobeDashboardFragment : Fragment() {
    private lateinit var viewModel: WardrobeDashboardViewModel
    private val wardrobeId: Long?
        get() = arguments?.getLong(KEY_WARDROBE_ID)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        container?.inflate(R.layout.fragment_wardrobe_dashboard)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModel()
        observeViewModel()
        setupListeners()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this)[WardrobeDashboardViewModel::class.java]
        viewModel.wardrobeId = wardrobeId
    }

    private fun observeViewModel() {
        with(viewModel) {
            loadingState.observe(viewLifecycleOwner) {
                progress isVisibleWhen this
                layoutContent isVisibleWhen not()
            }
            messageEvent.observeEvent(viewLifecycleOwner) {
                showMessage(it)
            }
            navigateUpEvent.observeEvent(viewLifecycleOwner) {
                navigateUp()
            }
        }
    }

    private fun setupListeners() {
        btnWardrobeDetails.setOnClickListener {
            wardrobeId?.also { id ->
                navigateTo(R.id.wardrobeDashboardFragmentToWardrobeDetails, WardrobeDetailsFragment.createExtras(id))
            }
        }
        btnWardrobeElements.setOnClickListener {
            wardrobeId?.also { id ->
                navigateTo(R.id.wardrobeDashboardFragmentToElementGroupFragment, ElementGroupFragment.createExtras(id))
            }
        }
        btnWardrobeGallery.setOnClickListener {
            wardrobeId?.also { id ->
                navigateTo(R.id.wardrobeDashboardFragmentToWardrobeGallery, WardrobeGalleryFragment.createExtras(id))
            }
        }
        btnRemoveWardrobe.setOnClickListener {
            viewModel.deleteWardrobe()
        }
    }

    companion object {

        fun createExtras(wardrobeId: Long): Bundle = Bundle().apply {
            putLong(KEY_WARDROBE_ID, wardrobeId)
        }
    }
}