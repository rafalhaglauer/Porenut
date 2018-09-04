//package com.wardrobes.porenut.ui.wardrobe.group
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.core.content.ContextCompat
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
//import androidx.recyclerview.widget.DividerItemDecoration
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.wardrobes.porenut.R
//import com.wardrobes.porenut.domain.Wardrobe
//import com.wardrobes.porenut.ui.extension.inflate
//import com.wardrobes.porenut.ui.vo.RequestType
//import com.wardrobes.porenut.ui.vo.Result
//import com.wardrobes.porenut.ui.vo.wardrobeId
//import com.wardrobes.porenut.ui.wardrobe.detail.WardrobeDetailsActivity
//import com.wardrobes.porenut.ui.wardrobe.manage.ManageWardrobeActivity
//
//private const val MANAGE_WARDROBE_REQUEST_CODE = 1
//
//class WardrobeGroupFragment : TabFragment() {
//    var wardrobeCreationType: Wardrobe.CreationType = Wardrobe.CreationType.STANDARD
//    var pageTitle: String = ""
//
//    private lateinit var wardrobeGroupViewModel: WardrobeGroupViewModel
//    private lateinit var creationTypeViewModel: CreationTypeViewModel
//
//    override fun getTitle(): String = pageTitle
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        wardrobeGroupViewModel = ViewModelProviders.of(this)[WardrobeGroupViewModel::class.java]
//        creationTypeViewModel = ViewModelProviders.of(activity!!)[CreationTypeViewModel::class.java]
//        observeViewModel()
//        btnAction.show()
//        btnAction.setOnClickListener {
//            launchActivity<ManageWardrobeActivity>(MANAGE_WARDROBE_REQUEST_CODE) {
//                wardrobeCreationType = wardrobeCreationType
//                requestType = RequestType.ADD
//            }
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ) = container?.inflate(R.layout.view_list)
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == MANAGE_WARDROBE_REQUEST_CODE) handleResult(resultCode, data)
//        else super.onActivityResult(requestCode, resultCode, data)
//    }
//
//    private fun observeViewModel() {
//        wardrobeGroupViewModel.viewState
//            .observe(this, Observer {
//                progress.setVisible(it!!.isLoading)
//                bind(it.viewEntities)
//                context?.showMessage(it.messageEvent)
//            })
//        wardrobeGroupViewModel.creationType = wardrobeCreationType
//
//        creationTypeViewModel.viewState
//            .observe(this, Observer {
//                when (wardrobeCreationType) {
//                    Wardrobe.CreationType.CUSTOM -> if (it?.shouldRefreshCustomWardrobes == true) refresh()
//                    Wardrobe.CreationType.STANDARD -> if (it?.shouldRefreshStandardWardrobes == true) refresh()
//                }
//            })
//    }
//
//    private fun bind(viewEntities: List<WardrobeViewEntity>) {
//        layoutContent.apply {
//            layoutManager = LinearLayoutManager(context)
//            addItemDecoration(
//                DividerItemDecoration(
//                    context,
//                    LinearLayoutManager.VERTICAL
//                ).apply {
//                    ContextCompat.getDrawable(context, R.drawable.divider)?.also { setDrawable(it) }
//                })
////            adapter = WardrobeGroupAdapter(viewEntities) {
////                wardrobeGroupViewModel.selectedWardrobe = it
////                launchActivity<WardrobeDetailsActivity>(MANAGE_WARDROBE_REQUEST_CODE) {
////                    wardrobeCreationType = wardrobeCreationType
////                    wardrobeId = wardrobeGroupViewModel.selectedWardrobeId
////                }
////            }
//        }
//    }
//
//    private fun handleResult(resultCode: Int, data: Intent?) {
//        creationTypeViewModel.refresh()
//        if (resultCode == Result.ADDED.value) {
//            data?.wardrobeId?.also {
//                launchActivity<WardrobeDetailsActivity>(MANAGE_WARDROBE_REQUEST_CODE) {
//                    wardrobeCreationType = wardrobeCreationType
//                    wardrobeId = it
//                }
//            }
//        }
//    }
//
//    private fun refresh() {
//        creationTypeViewModel.notifyRefreshed(wardrobeCreationType)
//        wardrobeGroupViewModel.refresh()
//    }
//}