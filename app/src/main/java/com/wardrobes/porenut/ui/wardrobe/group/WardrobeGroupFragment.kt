package com.wardrobes.porenut.ui.wardrobe.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.wardrobes.porenut.R
import com.wardrobes.porenut.domain.Wardrobe
import com.wardrobes.porenut.ui.common.extension.*
import com.wardrobes.porenut.ui.wardrobe.dashboard.WardrobeDashboardFragment
import kotlinx.android.synthetic.main.fragment_wardrobe_group.*

class WardrobeGroupFragment : Fragment() {
    private lateinit var viewModel: WardrobeGroupViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        container?.inflate(R.layout.fragment_wardrobe_group)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[WardrobeGroupViewModel::class.java]
        setupLayoutContent()
        observeViewModel()
        setupWardrobeSwitch()
        setupFab()
    }

    private fun setupLayoutContent() {
        contentWardrobeGroup.apply {
            layoutManager = LinearLayoutManager(context)
            setDivider(R.drawable.divider)
            adapter = WardrobeGroupAdapter {
                viewModel.getWardrobeId(it)?.also { id ->
                    navigateTo(R.id.wardrobeSectionToWardrobeDashboard, WardrobeDashboardFragment.createExtras(id))
                }
            }
        }
    }

    private fun observeViewModel() {
        viewModel.viewState
            .observe(viewLifecycleOwner) {
                progressWardrobeGroup.isVisibleWhen(isLoading)
                emptyListNotificationWardrobeGroup.isVisibleWhen(isEmptyListNotificationVisible)
                bind(viewEntities)
                context?.showMessage(errorMessage)
            }
    }

    private fun setupWardrobeSwitch() {
        switchWardrobeGroup.setOnToggleSwitchChangeListener { _, _ ->
            viewModel.wardrobeType = if (switchWardrobeGroup.checkedTogglePosition == 0) Wardrobe.Type.BOTTOM else Wardrobe.Type.UPPER
        }
        switchWardrobeGroup.checkedTogglePosition = 0
    }

    private fun setupFab() {
        btnActionWardrobeGroup.setOnClickListener {
            navigateTo(R.id.wardrobeSectionToWardrobeCreationTypeFragment)
        }
    }

    private fun bind(viewEntities: List<WardrobeViewEntity>) {
        (contentWardrobeGroup.adapter as WardrobeGroupAdapter).setItems(viewEntities)
    }
}