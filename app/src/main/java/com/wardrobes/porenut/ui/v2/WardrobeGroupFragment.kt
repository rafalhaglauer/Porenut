package com.wardrobes.porenut.ui.v2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.wardrobes.porenut.R
import com.wardrobes.porenut.domain.Wardrobe
import com.wardrobes.porenut.ui.extension.*
import com.wardrobes.porenut.ui.wardrobe.RequestType
import com.wardrobes.porenut.ui.wardrobe.group.WardrobeGroupAdapter
import com.wardrobes.porenut.ui.wardrobe.group.WardrobeGroupViewModel
import com.wardrobes.porenut.ui.wardrobe.group.WardrobeViewEntity
import com.wardrobes.porenut.ui.wardrobe.manage.ManageWardrobeActivity
import com.wardrobes.porenut.ui.wardrobe.manage.creationType
import com.wardrobes.porenut.ui.wardrobe.manage.requestType
import kotlinx.android.synthetic.main.fragment_wardrobe_group.*

class WardrobeGroupFragment : Fragment() {
    private lateinit var viewModel: WardrobeGroupViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_wardrobe_group)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[WardrobeGroupViewModel::class.java]
        setupLayoutContent()
        observeViewModel()
        setupWardrobeSwitch()
        setupFab()
    }

    private fun setupLayoutContent() {
        layoutContent.apply {
            layoutManager = LinearLayoutManager(context)
            setDivider(R.drawable.divider)
            adapter = WardrobeGroupAdapter {
                viewModel.selectedWardrobe = it
//                launchActivity<WardrobeDetailsActivity>(MANAGE_WARDROBE_REQUEST_CODE) {
//                    creationType = wardrobeCreationType
//                    wardrobeId = wardrobeGroupViewModel.selectedWardrobeId
//                }
            }
        }
    }

    private fun observeViewModel() {
        viewModel.viewState
            .observe(this) {
                layoutProgress.setVisible(isLoading)
                layoutEmptyListNotification.setVisible(isEmptyListNotificationVisible)
                bind(viewEntity)
                context?.showMessage(errorMessage)
            }
    }

    private fun setupWardrobeSwitch() {
        switchWardrobeGroup.setOnToggleSwitchChangeListener { _, _ ->
            viewModel.creationType = if (switchWardrobeGroup.checkedTogglePosition == 0) Wardrobe.CreationType.STANDARD else Wardrobe.CreationType.CUSTOM
        }
        switchWardrobeGroup.checkedTogglePosition = 0
    }

    private fun setupFab() {
        btnAction.setOnClickListener {
            launchActivity<ManageWardrobeActivity> {
                creationType = viewModel.creationType
                requestType = RequestType.ADD
            }
        }
    }

    private fun bind(viewEntities: List<WardrobeViewEntity>) {
        (layoutContent.adapter as WardrobeGroupAdapter).setItems(viewEntities)
    }
}