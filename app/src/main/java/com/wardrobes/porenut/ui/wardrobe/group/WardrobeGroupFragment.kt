package com.wardrobes.porenut.ui.wardrobe.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.common.extension.*
import com.wardrobes.porenut.ui.wardrobe.dashboard.WardrobeDashboardFragment
import kotlinx.android.synthetic.main.fragment_wardrobe_group.*

class WardrobeGroupFragment : Fragment() {

    private lateinit var viewModel: WardrobeGroupViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_wardrobe_group, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModel()
        setupLayoutContent()
        setupFab()
        observeViewModel()
        loadWardrobes()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this)[WardrobeGroupViewModel::class.java]
    }

    private fun setupLayoutContent() {
        with(contentWardrobeGroup) {
            setDivider(R.drawable.divider)
            adapter = WardrobeGroupAdapter(
                onItemSelected = { viewEntity -> viewModel.showDetails(viewEntity) },
                onAddDescription = { viewEntity -> viewModel.addDescription(viewEntity) }
            )
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            viewState.observe(viewLifecycleOwner) {
                progressWardrobeGroup.isVisibleWhen(isLoading)
                emptyListNotificationWardrobeGroup.isVisibleWhen(isEmptyListNotificationVisible)
                bind(viewEntities)
            }
            errorEvent.observeEvent(viewLifecycleOwner) { errorMessage ->
                showMessage(errorMessage)
            }
            showDetailsEvent.observeEvent(viewLifecycleOwner) { wardrobeId ->
                navigateTo(R.id.wardrobeSectionToWardrobeDashboard, WardrobeDashboardFragment.createExtras(wardrobeId))
            }
            addDescriptionEvent.observeEvent(viewLifecycleOwner) { wardrobeId ->
                showMessage("TODO -> $wardrobeId") // TODO
            }
        }
    }

    private fun setupFab() {
        btnActionWardrobeGroup.setOnClickListener {
            navigateTo(R.id.wardrobeSectionToWardrobeCreationTypeFragment)
        }
    }

    private fun loadWardrobes() {
        viewModel.loadWardrobes()
    }

    private fun bind(viewEntities: List<WardrobeViewEntity>) {
        (contentWardrobeGroup.adapter as WardrobeGroupAdapter).setItems(viewEntities)
    }
}