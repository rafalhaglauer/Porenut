//package com.wardrobes.porenut.ui.wardrobe.detail
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
//import com.wardrobes.porenut.ui.element.ElementDetailsActivity
//import com.wardrobes.porenut.ui.element.ManageElementActivity
//import com.wardrobes.porenut.ui.extension.inflate
//import com.wardrobes.porenut.ui.vo.RequestType
//import com.wardrobes.porenut.ui.vo.Result
//import com.wardrobes.porenut.ui.vo.elementId
//
//private const val MANAGE_ELEMENT_REQUEST_CODE = 1
//
//class ElementGroupFragment : TabFragment() {
//    private lateinit var elementGroupViewModel: ElementGroupViewModel
//
//    override fun getTitle(): String = "Elementy"
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ) = container?.inflate(R.layout.view_list)
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        elementGroupViewModel = ViewModelProviders.of(activity!!)[ElementGroupViewModel::class.java]
//        observeViewModel()
//        btnAction.setOnClickListener {
//            launchActivity<ManageElementActivity>(MANAGE_ELEMENT_REQUEST_CODE) {
//                wardrobeId = elementGroupViewModel.wardrobeId
//                requestType = RequestType.ADD
//            }
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == MANAGE_ELEMENT_REQUEST_CODE) handle(resultCode, data)
//        else super.onActivityResult(requestCode, resultCode, data)
//    }
//
//    private fun observeViewModel() {
//        elementGroupViewModel.viewState
//            .observe(this, Observer {
//                progress.setVisible(it!!.isLoading)
//                btnAction.setVisible(it.isAddButtonVisible)
//                bind(it.viewEntities)
//                context?.showMessage(it.messageEvent)
//            })
//    }
//
//    private fun bind(viewEntities: List<ElementViewEntity>) {
//        layoutContent.apply {
//            layoutManager = LinearLayoutManager(context)
//            addItemDecoration(
//                DividerItemDecoration(
//                    context,
//                    LinearLayoutManager.VERTICAL
//                ).apply {
//                    ContextCompat.getDrawable(context, R.drawable.divider)?.also { setDrawable(it) }
//                })
//            adapter = ElementGroupAdapter {
//                elementGroupViewModel.selectElement(it)
//                launchActivity<ElementDetailsActivity> {
//                    wardrobeId = elementGroupViewModel.wardrobeId
//                    wardrobeCreationType = elementGroupViewModel.creationType
//                    elementId = elementGroupViewModel.selectedElementId
//                }
//            }
//        }
//    }
//
//    private fun handle(resultCode: Int, data: Intent?) {
//        elementGroupViewModel.refresh()
//        if (resultCode == Result.ADDED.value) {
//            data?.elementId?.also {
//                launchActivity<ElementDetailsActivity>(MANAGE_ELEMENT_REQUEST_CODE) {
//                    wardrobeId = elementGroupViewModel.wardrobeId
//                    wardrobeCreationType = elementGroupViewModel.creationType
//                    elementId = it
//                }
//            }
//        }
//    }
//}