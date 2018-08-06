package com.wardrobes.porenut.ui.element

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.base.TabFragment
import com.wardrobes.porenut.ui.extension.*
import com.wardrobes.porenut.ui.wardrobe.RequestType
import com.wardrobes.porenut.ui.wardrobe.Result
import com.wardrobes.porenut.ui.wardrobe.detail.ElementViewEntity
import com.wardrobes.porenut.ui.wardrobe.manage.requestType
import kotlinx.android.synthetic.main.view_element_details.*

private const val MANAGE_ELEMENT_REQUEST_CODE = 1

class ElementDetailsFragment : TabFragment() {
    private lateinit var elementDetailsViewModel: ElementDetailsViewModel
    private lateinit var drillingGroupViewModel: DrillingGroupViewModel

    var pageTitle: String = ""

    override fun getTitle() = pageTitle

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        elementDetailsViewModel = ViewModelProviders.of(activity!!)[ElementDetailsViewModel::class.java]
        drillingGroupViewModel = ViewModelProviders.of(activity!!)[DrillingGroupViewModel::class.java]
        observeViewModel()
        btnAction.inflate(R.menu.menu_element_details)
        btnAction.setOnActionSelectedListener {
            when (it.id) {
                R.id.action_delete_element -> elementDetailsViewModel.delete()
                R.id.action_copy_element -> launchActivity<ManageElementActivity>(MANAGE_ELEMENT_REQUEST_CODE) {
                    elementId = elementDetailsViewModel.elementId
                    requestType = RequestType.COPY
                }
                R.id.action_edit_element -> launchActivity<ManageElementActivity>(MANAGE_ELEMENT_REQUEST_CODE) {
                    elementId = elementDetailsViewModel.elementId
                    requestType = RequestType.EDIT
                }
            }
            false
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.view_element_details)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == MANAGE_ELEMENT_REQUEST_CODE) handleResult(resultCode, data)
        else super.onActivityResult(requestCode, resultCode, data)
    }

    private fun observeViewModel() {
        elementDetailsViewModel.viewState.observe(this, Observer {
            if (it!!.isDeleted) {
                activity?.finishWithResult(Result.DELETED.value)
            } else {
                progress.setVisible(it.isLoading)
                layoutContent.setVisible(!it.isLoading)
                btnAction.setVisible(it.isManageButtonVisible)
                it.viewEntity?.also { bind(it) }
                context?.showMessage(it.errorMessage)
            }
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

    private fun handleResult(resultCode: Int, data: Intent?) {
        activity?.setResult(resultCode)
        data?.elementId?.also {
            drillingGroupViewModel.elementId = it
            elementDetailsViewModel.elementId = it
        }
    }
}