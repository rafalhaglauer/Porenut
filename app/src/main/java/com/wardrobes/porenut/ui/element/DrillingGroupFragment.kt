package com.wardrobes.porenut.ui.element

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.ViewGroup
import com.wardrobes.porenut.R
import com.wardrobes.porenut.domain.UNDEFINED_ID
import com.wardrobes.porenut.ui.base.TabFragment
import com.wardrobes.porenut.ui.drilling.ManageDrillingActivity
import com.wardrobes.porenut.ui.extension.inflate
import com.wardrobes.porenut.ui.extension.launchActivity
import com.wardrobes.porenut.ui.extension.setVisible
import com.wardrobes.porenut.ui.extension.showMessage
import com.wardrobes.porenut.ui.wardrobe.manage.drillingId
import kotlinx.android.synthetic.main.view_list.*

private const val MANAGE_DRILLING_REQUEST_CODE = 1

class DrillingGroupFragment : TabFragment() {
    var elementId: Long = UNDEFINED_ID

    private lateinit var drillingGroupViewModel: DrillingGroupViewModel

    override fun getTitle(): String = "Wiercenia"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = container?.inflate(R.layout.view_list)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        drillingGroupViewModel = ViewModelProviders.of(activity!!)[DrillingGroupViewModel::class.java]
        observeViewModel()
        btnAction.setOnClickListener {
            launchActivity<ManageDrillingActivity>(MANAGE_DRILLING_REQUEST_CODE) {
                elementId = drillingGroupViewModel.elementId
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == MANAGE_DRILLING_REQUEST_CODE) drillingGroupViewModel.refresh()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun observeViewModel() {
        drillingGroupViewModel.viewState
                .observe(this, Observer {
                    progress.setVisible(it!!.isLoading)
                    bind(it.viewEntities)
                    context?.showMessage(it.errorMessage)
                    btnAction.setVisible(it.isAddDrillingBtnVisible)
                })
        drillingGroupViewModel.elementId = elementId
    }

    private fun bind(viewEntities: List<DrillingViewEntity>) {
        contentLayout.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL).apply { ContextCompat.getDrawable(context, R.drawable.divider)?.also { setDrawable(it) } })
            adapter = DrillingGroupAdapter(viewEntities) {
                launchActivity<ManageDrillingActivity>(MANAGE_DRILLING_REQUEST_CODE) {
                    elementId = drillingGroupViewModel.elementId
                    drillingId = drillingGroupViewModel.getId(it)
                }
            }
        }
    }
}