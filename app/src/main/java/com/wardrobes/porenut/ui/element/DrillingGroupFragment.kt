package com.wardrobes.porenut.ui.element

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.ViewGroup
import com.wardrobes.porenut.R
import com.wardrobes.porenut.domain.UNDEFINED_ID
import com.wardrobes.porenut.ui.base.TabFragment
import com.wardrobes.porenut.ui.extension.inflate
import com.wardrobes.porenut.ui.extension.setVisible
import com.wardrobes.porenut.ui.extension.showMessage
import kotlinx.android.synthetic.main.view_list.*

class DrillingGroupFragment : TabFragment() {
    var elementId: Long = UNDEFINED_ID

    private lateinit var drillingGroupViewModel: DrillingGroupViewModel

    override fun getTitle(): String = "Wiercenia"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = container?.inflate(R.layout.view_list)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        drillingGroupViewModel = ViewModelProviders.of(activity!!)[DrillingGroupViewModel::class.java]
        observeViewModel()
    }

    private fun observeViewModel() {
        drillingGroupViewModel.viewState
                .observe(this, Observer {
                    progress.setVisible(it!!.isLoading)
                    bind(it.viewEntities)
                    context?.showMessage(it.errorMessage)
                })
        drillingGroupViewModel.elementId = elementId
    }

    private fun bind(viewEntities: List<DrillingViewEntity>) {
        contentLayout.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = DrillingGroupAdapter(viewEntities) {

            }
        }
    }
}