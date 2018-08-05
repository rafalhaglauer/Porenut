package com.wardrobes.porenut.ui.element

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.base.TabFragment
import com.wardrobes.porenut.ui.extension.inflate
import com.wardrobes.porenut.ui.extension.setVisible
import com.wardrobes.porenut.ui.extension.showMessage
import com.wardrobes.porenut.ui.wardrobe.detail.ElementViewEntity
import kotlinx.android.synthetic.main.view_element_details.*

class ElementDetailsFragment : TabFragment() {
    private lateinit var viewModel: ElementDetailsViewModel

    var pageTitle: String = ""

    override fun getTitle() = pageTitle

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!)[ElementDetailsViewModel::class.java]
        observeViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.view_element_details)
    }

    private fun observeViewModel() {
        viewModel.viewState.observe(this, Observer {
            progress.setVisible(it!!.isLoading)
            layoutContent.setVisible(!it.isLoading)
            it.viewEntity?.also { bind(it) }
            context?.showMessage(it.errorMessage)
        })
    }

    private fun bind(viewEntity: ElementViewEntity) {
        with(viewEntity) {
            txtElementName.text = name
            txtElementLength.text = length
            txtElementWidth.text = width
            txtElementHeight.text = height
        }
    }
}