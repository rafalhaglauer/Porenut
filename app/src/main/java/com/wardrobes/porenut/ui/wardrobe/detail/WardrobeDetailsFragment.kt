package com.wardrobes.porenut.ui.wardrobe.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.extension.*
import com.wardrobes.porenut.ui.viewer.model.Model
import com.wardrobes.porenut.ui.viewer.view.ModelActivity
import kotlinx.android.synthetic.main.fragment_wardrobe_details.*

private const val KEY_WARDROBE_ID = "key-wardrobe-id"

class WardrobeDetailsFragment : Fragment() {
    private lateinit var viewModel: WardrobeDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        container?.inflate(R.layout.fragment_wardrobe_details)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModel()
        observeViewModel()
        setupActionButton()
        unpackArguments()
        launchActivity<ModelActivity> {
            putExtra("key-wardrobe-id", arguments?.getLong(KEY_WARDROBE_ID))
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this)[WardrobeDetailViewModel::class.java]
    }

    private fun observeViewModel() {
        observeViewState()
        observeEvents()
    }

    private fun observeViewState() {
        viewModel.viewState
            .observe(viewLifecycleOwner) {
                progressWardrobeDetail isVisibleWhen isLoading
                contentWardrobeDetail isVisibleWhen !isLoading
                bind(viewEntity)
            }
    }

    private fun observeEvents() {
        viewModel.messageEvent
            .observeEvent(viewLifecycleOwner) {
                showMessage(it)
            }
        viewModel.navigateUpEvent
            .observeEvent(viewLifecycleOwner) {
                navigateUp()
            }
    }

    fun bind(viewEntity: WardrobeDetailViewEntity) {
        with(viewEntity) {
            txtWardrobeSymbol.text = symbol
            txtWardrobeWidth.text = width
            txtWardrobeHeight.text = height
            txtWardrobeDepth.text = depth
            txtWardrobeType.text = context?.getString(type)
        }
    }

    fun showModel(model: Model) {
        //TODO Model 3d!!!
//        layoutModel.show()
//        layoutModel.removeAllViews()
//        layoutModel.addView(ModelSurfaceView(context, model))
    }

    private fun setupActionButton() {
        // TODO Just edit!
        // SETUP EDITION
        btnActionWardrobeDetail.inflate(R.menu.menu_wardrobe_details)
    }

    private fun unpackArguments() {
        viewModel.wardrobeId = arguments?.getLong(KEY_WARDROBE_ID)
    }

    companion object {

        fun createExtras(wardrobeId: Long): Bundle = Bundle().apply {
            putLong(KEY_WARDROBE_ID, wardrobeId)
        }
    }
}

/*

        ModelLoadTask().execute(Uri.parse("file:///${Environment.getExternalStorageDirectory()}/Download/wardrobe_OBJ.obj"))



*     private inner class ModelLoadTask : AsyncTask<Uri, Int, Model>() {
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


    //            arguments?.wardrobeId?.also {
//                launchActivity<ManageWardrobeActivity>(REQUEST_EDIT_WARDROBE) {
//                    wardrobeId = it
//                    requestType = RequestType.EDIT
//                }
//            }


* */