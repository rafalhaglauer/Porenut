//package com.wardrobes.porenut.ui.element
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
//import com.wardrobes.porenut.R
//import com.wardrobes.porenut.ui.extension.inflate
//import com.wardrobes.porenut.ui.vo.RequestType
//import com.wardrobes.porenut.ui.vo.Result
//import com.wardrobes.porenut.ui.vo.elementId
//import com.wardrobes.porenut.ui.wardrobe.detail.ElementViewEntity
//
//private const val MANAGE_ELEMENT_REQUEST_CODE = 1
//
//class ElementDetailsFragment : TabFragment() {
//    private lateinit var elementDetailsViewModel: ElementDetailsViewModel
//    private lateinit var drillingGroupViewModel: DrillingGroupViewModel
//
//    var pageTitle: String = ""
//
//    override fun getTitle() = pageTitle
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        elementDetailsViewModel =
//                ViewModelProviders.of(activity!!)[ElementDetailsViewModel::class.java]
//        drillingGroupViewModel =
//                ViewModelProviders.of(activity!!)[DrillingGroupViewModel::class.java]
//        observeViewModel()
//        btnAction.inflate(R.menu.menu_element_detail)
//        btnAction.setOnActionSelectedListener {
//            when (it.id) {
//                R.id.action_delete_element -> elementDetailsViewModel.delete()
//                R.id.action_copy_element -> launchActivity<ManageElementActivity>(
//                    MANAGE_ELEMENT_REQUEST_CODE
//                ) {
//                    elementId = elementDetailsViewModel.elementId
//                    requestType = RequestType.COPY
//                }
//                R.id.action_edit_element -> launchActivity<ManageElementActivity>(
//                    MANAGE_ELEMENT_REQUEST_CODE
//                ) {
//                    elementId = elementDetailsViewModel.elementId
//                    requestType = RequestType.EDIT
//                }
//            }
//            false
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return container?.inflate(R.layout.view_element_detail)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == MANAGE_ELEMENT_REQUEST_CODE) handleResult(resultCode, data)
//        else super.onActivityResult(requestCode, resultCode, data)
//    }
//
//    private fun observeViewModel() {
//        elementDetailsViewModel.viewState.observe(this, Observer {
//            if (it!!.isDeleted) {
//                activity?.finishWithResult(Result.DELETED.value)
//            } else {
//                progress.setVisible(it.isLoading)
//                layoutContent.setVisible(!it.isLoading)
//                btnAction.setVisible(it.isManageButtonVisible)
//                it.viewEntities?.also { bind(it) }
//                context?.showMessage(it.messageEvent)
//            }
//        })
//    }
//
//    private fun bind(viewEntities: ElementViewEntity) {
//        with(viewEntities) {
//            txtElementName.text = name
//            txtElementLength.text = length
//            txtElementWidth.text = width
//            txtElementHeight.text = height
//        }
//    }
//
//    private fun handleResult(resultCode: Int, data: Intent?) {
//        activity?.setResult(resultCode)
//        data?.elementId?.also {
//            drillingGroupViewModel.elementId = it
//            elementDetailsViewModel.elementId = it
//        }
//    }
//}