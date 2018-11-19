package com.wardrobes.porenut.ui.wardrobe.detail

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.element.manage.ManageElementActivity
import com.wardrobes.porenut.ui.extension.*
import com.wardrobes.porenut.ui.viewer.model.Model
import com.wardrobes.porenut.ui.viewer.model.ObjModel
import com.wardrobes.porenut.ui.vo.*
import kotlinx.android.synthetic.main.fragment_wardrobe_tab.*
import java.io.InputStream

private const val REQUEST_EDIT_WARDROBE = 1
private const val REQUEST_ADD_ELEMENT = 2

class WardrobeTabFragment : Fragment() {
    private lateinit var viewModel: WardrobeTabViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_wardrobe_tab)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[WardrobeTabViewModel::class.java]
        setupWardrobeSwitch()
        setupViewModel()
        setupListeners()
        viewModel.showDetails()
        ModelLoadTask().execute(Uri.parse("file:///${Environment.getExternalStorageDirectory()}/Download/wardrobe_OBJ.obj"))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_EDIT_WARDROBE && resultCode == Result.MODIFIED.value) {
            viewModel.showDetails()
        }
        if (requestCode == REQUEST_ADD_ELEMENT && resultCode == Result.ADDED.value) {
            viewModel.showElementGroup()
        }
    }

    private fun setupWardrobeSwitch() {
        switchWardrobeTab.setOnToggleSwitchChangeListener { _, _ ->
            when (switchWardrobeTab.checkedTogglePosition) {
                0 -> viewModel.showDetails()
                1 -> viewModel.showElementGroup()
                2 -> viewModel.showGallery()
            }
        }
    }

    private fun setupViewModel() {
        viewModel.wardrobeId = arguments?.wardrobeId
        viewModel.viewState
            .observe(viewLifecycleOwner) {
                viewWardrobeDetail.setVisible(areDetailsVisible)
                viewElementGroup.setVisible(isElementGroupVisible)
                viewWardrobeGallery.setVisible(isGalleryVisible)
            }
        viewModel.messageEvent
            .observe(viewLifecycleOwner) {
                getContentIfNotHandled()?.also {
                    context?.showMessage(it)
                }
            }
        viewModel.navigateUpEvent
            .observe(viewLifecycleOwner) {
                getContentIfNotHandled()?.also {
                    navigateUp()
                }
            }
        viewModel.detailViewState
            .observe(viewLifecycleOwner) {
                viewEntity?.also { viewWardrobeDetail.bind(it) }
                viewWardrobeDetail.showProgress(isLoading)
                viewWardrobeDetail.showContent(!isLoading)
            }
        viewModel.elementGroupViewState
            .observe(viewLifecycleOwner) {
                viewElementGroup.bind(viewEntities)
                viewElementGroup.showProgress(isLoading)
                viewElementGroup.showContent(!isLoading)
                viewElementGroup.showEmptyListNotification(isEmptyListNotificationVisible)
                viewElementGroup.showActionButton(isAddElementActionAvailable)
            }
    }

    private fun setupListeners() {
        viewWardrobeDetail.onDelete = {
            viewModel.deleteWardrobe()
        }
        viewWardrobeDetail.onCopy = {
            // TODO
        }
        viewWardrobeDetail.onEdit = {
            // TODO
//            arguments?.wardrobeId?.also {
//                launchActivity<ManageWardrobeActivity>(REQUEST_EDIT_WARDROBE) {
//                    wardrobeId = it
//                    requestType = RequestType.EDIT
//                }
//            }
        }

        viewElementGroup.onAdd = {
            arguments?.wardrobeId?.also {
                launchActivity<ManageElementActivity>(REQUEST_ADD_ELEMENT) {
                    wardrobeId = it
                    requestType = RequestType.ADD
                }
            }
        }
        viewElementGroup.onSelected = {
            navigateTo(R.id.wardrobeTabToElementTab) {
                elementId = viewModel.getElementId(it)
                arguments?.wardrobeId?.also { wardrobeId = it }
            }
        }
    }

    private inner class ModelLoadTask : AsyncTask<Uri, Int, Model>() {
        override fun doInBackground(vararg file: Uri): Model? {
            var stream: InputStream? = null
            try {
                val uri = file[0]
                val cr = context?.contentResolver
                stream = cr?.openInputStream(uri)
                return stream?.let { ObjModel(it) }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                stream?.close()
            }
            return null
        }

        override fun onProgressUpdate(vararg values: Int?) {}

        override fun onPostExecute(model: Model?) {
            model?.also { viewWardrobeDetail.showModel(it) }
        }
    }
}