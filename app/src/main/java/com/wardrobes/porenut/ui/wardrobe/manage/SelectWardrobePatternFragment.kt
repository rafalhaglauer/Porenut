package com.wardrobes.porenut.ui.wardrobe.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.common.extension.*
import com.wardrobes.porenut.ui.pattern.wardrobe.group.WardrobePatternGroupAdapter
import com.wardrobes.porenut.ui.pattern.wardrobe.group.WardrobePatternGroupViewModel
import com.wardrobes.porenut.ui.pattern.wardrobe.group.WardrobePatternViewEntity
import kotlinx.android.synthetic.main.fragment_wardrobe_pattern_group.*

class SelectWardrobePatternFragment : Fragment() {

    private val viewModel: WardrobePatternGroupViewModel by injectViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_wardrobe_pattern_group, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupToolbar()
        setupContent()
        hideFab()
        observeViewModel()
        loadWardrobes()
    }

    private fun setupToolbar() {
        setTitle(R.string.l_wardrobe_patterns)
    }

    private fun setupContent() {
        with(contentWardrobeGroup) {
            setDivider(R.drawable.divider)
            adapter = WardrobePatternGroupAdapter(
                onItemSelected = { viewEntity -> viewModel.showDetails(viewEntity) },
                onAddDescription = { }
            )
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            viewState.observe(viewLifecycleOwner) {
                progressWardrobePatternGroup.isVisibleWhen(isLoading)
                emptyListNotificationWardrobePatternGroup.isVisibleWhen(isEmptyListNotificationVisible)
                bind(viewEntities)
            }
            errorEvent.observeEvent(viewLifecycleOwner) { errorMessage ->
                showMessage(errorMessage)
            }
            showDetailsEvent.observeEvent(viewLifecycleOwner) { patternId ->
                navigateTo(R.id.selectWardrobePatternFragmentToManageWardrobeFragment, ManageWardrobeFragment.createPatternExtras(patternId))
            }
        }
    }

    private fun hideFab() {
        btnActionWardrobeGroup.begone()
    }

    private fun loadWardrobes() {
        viewModel.loadWardrobes()
    }

    private fun bind(viewEntities: List<WardrobePatternViewEntity>) {
        viewEntities.forEach { it.isAddDescriptionOptionVisible = false }
        (contentWardrobeGroup.adapter as WardrobePatternGroupAdapter).setItems(viewEntities)
    }
}