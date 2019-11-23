package com.wardrobes.porenut.ui.pattern.drilling.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.common.extension.*
import com.wardrobes.porenut.ui.pattern.drilling.manage.ManageDrillingPatternFragment
import kotlinx.android.synthetic.main.fragment_drilling_pattern_group.*

private const val KEY_ELEMENT_PATTERN_ID = "key-element-pattern-id"

class DrillingPatternGroupFragment : Fragment() {

    private val viewModel: DrillingPatternGroupViewModel by injectViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_drilling_pattern_group, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupToolbar()
        setupContent()
        setupFab()
        observeViewModel()
        loadDrillings()
    }

    private fun setupToolbar() {
        setTitle(R.string.l_drilling_patterns)
    }

    private fun setupContent() {
        with(contentDrillingGroup) {
            setDivider(R.drawable.divider)
            adapter = DrillingPatternGroupAdapter { viewEntity -> viewModel.showDetails(viewEntity) }
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            viewState.observe(viewLifecycleOwner) {
                progressDrillingPatternGroup.isVisibleWhen(isLoading)
                emptyListNotificationDrillingPatternGroup.isVisibleWhen(isEmptyListNotificationVisible)
                bind(viewEntities)
            }   
            errorEvent.observeEvent(viewLifecycleOwner) { errorMessage ->
                showMessage(errorMessage)
            }
            showDetailsEvent.observeEvent(viewLifecycleOwner) { patternId ->
                navigateTo(R.id.drillingPatternGroupToManageDrillingPattern, ManageDrillingPatternFragment.createManageExtras(patternId))
            }
        }
    }

    private fun setupFab() {
        btnActionDrillingGroup.setOnClickListener {
            navigateTo(R.id.drillingPatternGroupToManageDrillingPattern, ManageDrillingPatternFragment.createNewExtras(getWardrobeId()))
        }
    }

    private fun loadDrillings() {
        viewModel.loadDrillings(getWardrobeId())
    }

    private fun bind(viewEntities: List<DrillingPatternViewEntity>) {
        (contentDrillingGroup.adapter as DrillingPatternGroupAdapter).setItems(viewEntities)
    }

    private fun getWardrobeId(): String = arguments?.getString(KEY_ELEMENT_PATTERN_ID).orEmpty()

    companion object {

        fun createExtras(elementPatternId: String): Bundle = Bundle().apply {
            putString(KEY_ELEMENT_PATTERN_ID, elementPatternId)
        }
    }
}