//package com.wardrobes.porenut.ui.wardrobe.detail
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
//import com.wardrobes.porenut.R
//import com.wardrobes.porenut.ui.extension.inflate
//import com.wardrobes.porenut.ui.vo.RequestType
//import com.wardrobes.porenut.ui.vo.Result
//import com.wardrobes.porenut.ui.vo.wardrobeId
//import com.wardrobes.porenut.ui.wardrobe.manage.ManageWardrobeActivity
//
//private const val MANAGE_WARDROBE_REQUEST_CODE = 1
//
//class WardrobeDetailsFragment : TabFragment() {
//    private lateinit var wardrobeDetailsViewModel: WardrobeDetailsViewModel
//    private lateinit var elementGroupViewModel: ElementGroupViewModel
//
//    var pageTitle: String = ""
//
//    override fun getTitle(): String = pageTitle
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        wardrobeDetailsViewModel =
//                ViewModelProviders.of(activity!!)[WardrobeDetailsViewModel::class.java]
//        elementGroupViewModel = ViewModelProviders.of(activity!!)[ElementGroupViewModel::class.java]
//        observeViewModel()
//        btnAction.inflate(R.menu.menu_wardrobe_details)
//        btnAction.setOnActionSelectedListener {
//            when (it.id) {
//                R.id.action_delete_wardrobe -> {
//                    wardrobeDetailsViewModel.delete()
//                }
//                R.id.action_copy_wardrobe -> {
//                    launchActivity<ManageWardrobeActivity>(MANAGE_WARDROBE_REQUEST_CODE) {
//                        wardrobeId = wardrobeDetailsViewModel.wardrobeId
//                        requestType = RequestType.COPY
//                    }
//                }
//                R.id.action_edit_wardrobe -> {
//                    launchActivity<ManageWardrobeActivity>(MANAGE_WARDROBE_REQUEST_CODE) {
//                        wardrobeId = wardrobeDetailsViewModel.wardrobeId
//                        requestType = RequestType.EDIT
//                    }
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
//    ) = container?.inflate(R.layout.view_wardrobe_detail)
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == MANAGE_WARDROBE_REQUEST_CODE) handleResult(resultCode, data)
//        else super.onActivityResult(requestCode, resultCode, data)
//    }
//
//    private fun observeViewModel() {
//        wardrobeDetailsViewModel.viewState
//            .observe(this, Observer {
//                if (it!!.isDeleted) {
//                    activity?.finishWithResult(Result.DELETED.value)
//                } else {
//                    progress.setVisible(it.isLoading)
//                    layoutContent.setVisible(!it.isLoading)
//                    bind(it.wardrobeViewEntity)
//                    context?.showMessage(it.messageEvent)
//                }
//            })
//    }
//
//    private fun bind(viewEntities: WardrobeDetailViewEntity) {
//        with(viewEntities) {
//            txtWardrobeSymbol.text = symbol
//            txtWardrobeWidth.text = width
//            txtWardrobeHeight.text = height
//            txtWardrobeDepth.text = depth
//            txtWardrobeType.text = if (type != 0) getString(type) else ""
//        }
//    }
//
//    private fun handleResult(resultCode: Int, data: Intent?) {
//        activity?.setResult(resultCode)
//        data?.wardrobeId?.also {
//            elementGroupViewModel.wardrobeId = it
//            wardrobeDetailsViewModel.wardrobeId = it
//        }
//    }
//}