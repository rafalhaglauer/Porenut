//package com.wardrobes.porenut.ui.element
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
//import com.wardrobes.porenut.domain.UNDEFINED_ID
//import com.wardrobes.porenut.ui.drilling.ManageDrillingActivity
//import com.wardrobes.porenut.ui.element.composition.ManageCompositionActivity
//import com.wardrobes.porenut.ui.extension.inflate
//
//private const val MANAGE_DRILLING_REQUEST_CODE = 1
//
//class DrillingGroupFragment : TabFragment() {
//    var elementId: Long = UNDEFINED_ID
//
//    private lateinit var drillingGroupViewModel: DrillingGroupViewModel
//
//    override fun getTitle(): String = "Wiercenia"
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = container?.inflate(R.layout.view_list)
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        drillingGroupViewModel = ViewModelProviders.of(activity!!)[DrillingGroupViewModel::class.java]
//        observeViewModel()
//        btnAction.setOnClickListener {
//            launchActivity<ManageCompositionActivity>(MANAGE_DRILLING_REQUEST_CODE) {
//                elementId = drillingGroupViewModel.elementId
//                wardrobeId = drillingGroupViewModel.wardrobeId
//            }
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == MANAGE_DRILLING_REQUEST_CODE) drillingGroupViewModel.refresh()
//        super.onActivityResult(requestCode, resultCode, data)
//    }
//
//    private fun observeViewModel() {
//        drillingGroupViewModel.viewState
//            .observe(this, Observer {
//                progress.setVisible(it!!.isLoading)
//                bind(it.viewEntities)
//                context?.showMessage(it.messageEvent)
//                btnAction.setVisible(it.isAddDrillingBtnVisible)
//            })
//        drillingGroupViewModel.elementId = elementId
//    }
//
//    private fun bind(viewEntities: List<DrillingViewEntity>) {
//        layoutContent.apply {
//            layoutManager = LinearLayoutManager(context)
//            addItemDecoration(
//                DividerItemDecoration(
//                    context,
//                    LinearLayoutManager.VERTICAL
//                ).apply {
//                    ContextCompat.getDrawable(context, R.drawable.divider)?.also { setDrawable(it) }
//                })
//            adapter = DrillingGroupAdapter(viewEntities) {
//                launchActivity<ManageDrillingActivity>(MANAGE_DRILLING_REQUEST_CODE) {
//                    elementId = drillingGroupViewModel.elementId
//                    drillingId = drillingGroupViewModel.getId(it)
//                }
//            }
//        }
//    }
//}