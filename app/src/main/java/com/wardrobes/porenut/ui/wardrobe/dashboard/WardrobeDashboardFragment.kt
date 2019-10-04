package com.wardrobes.porenut.ui.wardrobe.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.wardrobes.porenut.R
import com.wardrobes.porenut.pdf.DefaultPdfGenerator
import com.wardrobes.porenut.ui.common.extension.*
import com.wardrobes.porenut.ui.element.group.ElementGroupFragment
import com.wardrobes.porenut.ui.photo.PhotoActivity
import com.wardrobes.porenut.ui.wardrobe.detail.WardrobeDetailsFragment
import kotlinx.android.synthetic.main.fragment_wardrobe_dashboard.*

private const val KEY_WARDROBE_ID = "key-wardrobe-id"

private const val REQUEST_CODE_TAKE_PHOTO = 1

class WardrobeDashboardFragment : Fragment() {

    private lateinit var viewModel: WardrobeDashboardViewModel

    private val wardrobeId: String
        get() = arguments?.getString(KEY_WARDROBE_ID).orEmpty()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        container?.inflate(R.layout.fragment_wardrobe_dashboard)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModel()
        observeViewModel()
        setupListeners()
        loadWardrobe()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_wardrobe, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.manageWardrobe -> showDetails()
            R.id.deleteWardrobe -> remove()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }
// TODO
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_CODE_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
//            PhotoActivity.getPhotoPath(data)?.also { path ->
                //                val file = File(path)
//                val service = BaseProvider.retrofit.create(AttachmentService::class.java)
//                val requestFile = RequestBody.create(MediaType.parse(path), file)
//                val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
//                arguments?.getString(KEY_WARDROBE_ID).takeIf { it != -1L }?.also {
//                    service.uploadPhoto(body, it)
//                        .fetchStateFullModel(
//                            onLoading = { showMessage("Loading") },
//                            onSuccess = { showMessage("Done") },
//                            onError = { showMessage("Error") }
//                        )
//                }
//                viewModel.uploadPhoto(File(path))
//            }
//        }
//    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this)[WardrobeDashboardViewModel::class.java]
        viewModel.pdfGenerator = context?.let { DefaultPdfGenerator(it) }
    }

    private fun observeViewModel() {
        with(viewModel) {
            //            loadingState.observe(viewLifecycleOwner) {
//                progress isVisibleWhen this
//                btnWardrobeGallery isVisibleWhen not()
//            }
            viewState.observe(viewLifecycleOwner) {
                setTitle(symbol)
            }
            messageEvent.observeEvent(viewLifecycleOwner) {
                showMessage(it)
            }
            navigateUpEvent.observeEvent(viewLifecycleOwner) {
                navigateUp()
            }
        }

        val sglm = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutGallery.layoutManager = sglm

        val imageList = ArrayList<String>()
        imageList.add("https://farm5.staticflickr.com/4403/36538794702_83fd8b63b7_c.jpg")
        imageList.add("https://farm5.staticflickr.com/4354/35684440714_434610d1d6_c.jpg")
        imageList.add("https://farm5.staticflickr.com/4301/35690634410_f5d0e312cb_c.jpg")
        imageList.add("https://farm4.staticflickr.com/3854/32890526884_7dc068fedd_c.jpg")
        imageList.add("https://farm8.staticflickr.com/7787/18143831616_a239c78056_c.jpg")
        imageList.add("https://farm9.staticflickr.com/8745/16657401480_57653ac8b0_c.jpg")
        imageList.add("https://farm3.staticflickr.com/2917/14144166232_44613c53c7_c.jpg")
        imageList.add("https://farm8.staticflickr.com/7453/13960410788_3dd02b7a02_c.jpg")
        imageList.add("https://farm1.staticflickr.com/920/29297133218_de38a7e4c8_c.jpg")
        imageList.add("https://farm2.staticflickr.com/1788/42989123072_6720c9608d_c.jpg")
        imageList.add("https://farm1.staticflickr.com/888/29062858008_89851766c9_c.jpg")
        imageList.add("https://farm2.staticflickr.com/1731/27940806257_8067196b41_c.jpg")
        imageList.add("https://farm1.staticflickr.com/884/42745897912_ff65398e38_c.jpg")
        imageList.add("https://farm2.staticflickr.com/1829/27971893037_1858467f9a_c.jpg")
        imageList.add("https://farm2.staticflickr.com/1822/41996470025_414452d7a0_c.jpg")
        imageList.add("https://farm2.staticflickr.com/1793/42937679651_3094ebb2b9_c.jpg")
        imageList.add("https://farm1.staticflickr.com/892/42078661914_b940d96992_c.jpg")
        val igka = WardrobeGalleryAdapter(context!!, imageList)
        layoutGallery.adapter = igka
    }

    private fun setupListeners() {
        btnTakePhoto.setOnClickListener {
            val intent = Intent(it.context, PhotoActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO)
        }
        btnWardrobeElements.setOnClickListener {
            navigateTo(
                R.id.wardrobeDashboardFragmentToElementGroupFragment,
                ElementGroupFragment.createExtras(wardrobeId)
            )
        }
//        btnWardrobeGallery.setOnClickListener {
//            navigateTo(
//                R.id.wardrobeDashboardFragmentToWardrobeGallery,
//                WardrobeGalleryFragment.createExtras(wardrobeId)
//            )
//        }
        btnGeneratePdf.setOnClickListener {
            viewModel.generatePdf()
        }
    }

    private fun loadWardrobe() {
        viewModel.loadWardrobe(wardrobeId)
    }

    private fun showDetails() {
        navigateTo(R.id.wardrobeDashboardFragmentToWardrobeDetails, WardrobeDetailsFragment.createExtras(wardrobeId))
    }

    private fun remove() {
        viewModel.deleteWardrobe()
    }

    companion object {

        fun createExtras(wardrobeId: String): Bundle = Bundle().apply {
            putString(KEY_WARDROBE_ID, wardrobeId)
        }
    }
}