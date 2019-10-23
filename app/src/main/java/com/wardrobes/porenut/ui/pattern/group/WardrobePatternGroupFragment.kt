package com.wardrobes.porenut.ui.pattern.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.common.extension.*
import com.wardrobes.porenut.ui.pattern.details.WardrobePatternDetailsFragment
import kotlinx.android.synthetic.main.fragment_wardrobe_pattern_group.*

class WardrobePatternGroupFragment : Fragment() {

    private val viewModel: WardrobePatternGroupViewModel by injectViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_wardrobe_pattern_group, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupToolbar()
        setupContent()
        setupFab()
        observeViewModel()
        loadWardrobes()
    }

    private fun setupToolbar() {
        setTitle(getString(R.string.app_name)) // TODO
    }

    private fun setupContent() {
        with(contentWardrobeGroup) {
            setDivider(R.drawable.divider)
            adapter = WardrobePatternGroupAdapter(
                onItemSelected = { viewEntity -> viewModel.showDetails(viewEntity) },
                onAddDescription = { viewEntity -> viewModel.addDescription(viewEntity) }
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
                navigateTo(R.id.wardrobePatternGroupToWardrobePatternDetails, WardrobePatternDetailsFragment.createExtras(patternId)) // TODO patternId!
            }
            addDescriptionEvent.observeEvent(viewLifecycleOwner) { patternId ->
                TODO("Not implemented yet!")
            }
        }
    }

    private fun setupFab() {
        btnActionWardrobeGroup.setOnClickListener {
            navigateTo(R.id.wardrobePatternGroupToManageWardrobePattern)
        }
    }

    private fun loadWardrobes() {
        viewModel.loadWardrobes()
    }

    private fun bind(viewEntities: List<WardrobePatternViewEntity>) {
        (contentWardrobeGroup.adapter as WardrobePatternGroupAdapter).setItems(viewEntities)
    }
}