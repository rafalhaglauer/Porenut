package com.wardrobes.porenut.ui.pattern.element.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.common.extension.*
import com.wardrobes.porenut.ui.pattern.drilling.group.DrillingPatternGroupFragment
import com.wardrobes.porenut.ui.pattern.element.manage.ManageElementPatternFragment
import kotlinx.android.synthetic.main.fragment_element_pattern_group.*

private const val KEY_WARDROBE_PATTERN_ID = "key-wardrobe-pattern-id"

class ElementPatternGroupFragment : Fragment() {

    private val viewModel: ElementPatternGroupViewModel by injectViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_element_pattern_group, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupToolbar()
        setupContent()
        setupFab()
        observeViewModel()
        loadElements()
    }

    private fun setupToolbar() {
        setTitle(R.string.l_element_patterns)
    }

    private fun setupContent() {
        with(contentElementGroup) {
            setDivider(R.drawable.divider)
            adapter = ElementPatternGroupAdapter(
                onItemSelected = { viewEntity -> viewModel.showDetails(viewEntity) },
                onShowDrillings = { viewEntity -> viewModel.showDrillings(viewEntity) }
            )
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            viewState.observe(viewLifecycleOwner) {
                progressElementPatternGroup.isVisibleWhen(isLoading)
                emptyListNotificationElementPatternGroup.isVisibleWhen(isEmptyListNotificationVisible)
                bind(viewEntities)
            }
            errorEvent.observeEvent(viewLifecycleOwner) { errorMessage ->
                showMessage(errorMessage)
            }
            showDetailsEvent.observeEvent(viewLifecycleOwner) { patternId ->
                navigateTo(R.id.elementPatternGroupToManageElementPattern, ManageElementPatternFragment.createManageExtras(patternId))
            }
            showDrillingsEvent.observeEvent(viewLifecycleOwner) { patternId ->
                navigateTo(R.id.elementPatternGroupToDrillingPatternGroup, DrillingPatternGroupFragment.createExtras(patternId))
            }
        }
    }

    private fun setupFab() {
        btnActionElementGroup.setOnClickListener {
            navigateTo(R.id.elementPatternGroupToManageElementPattern, ManageElementPatternFragment.createNewExtras(getWardrobeId()))
        }
    }

    private fun loadElements() {
        viewModel.loadElements(getWardrobeId())
    }

    private fun bind(viewEntities: List<ElementPatternViewEntity>) {
        (contentElementGroup.adapter as ElementPatternGroupAdapter).setItems(viewEntities)
    }

    private fun getWardrobeId(): String = arguments?.getString(KEY_WARDROBE_PATTERN_ID).orEmpty()

    companion object {

        fun createExtras(wardrobePatternId: String): Bundle = Bundle().apply {
            putString(KEY_WARDROBE_PATTERN_ID, wardrobePatternId)
        }
    }
}