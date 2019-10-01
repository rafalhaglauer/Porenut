package com.wardrobes.porenut.ui.wardrobe.gallery

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import com.stfalcon.imageviewer.StfalconImageViewer
import com.stfalcon.imageviewer.loader.ImageLoader
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.common.extension.*
import com.wardrobes.porenut.ui.photo.PhotoActivity
import kotlinx.android.synthetic.main.fragment_wardrobe_gallery.*
import java.io.File

private const val REQUEST_CODE_TAKE_PHOTO = 1
private const val KEY_WARDROBE_ID = "key-wardrobe-id"

class WardrobeGalleryFragment : Fragment() {
    private lateinit var viewModel: WardrobeGalleryViewModel
    private lateinit var viewer: StfalconImageViewer<String>
    private val imageViews by lazy {
        mapOf<Int, ImageView>(
            0 to postersFirstImage,
            1 to postersSecondImage,
            2 to postersThirdImage,
            3 to postersFourthImage,
            4 to postersFifthImage,
            5 to postersSixthImage,
            6 to postersSeventhImage,
            7 to postersEighthImage,
            8 to postersNinthImage
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        container?.inflate(R.layout.fragment_wardrobe_gallery)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModel()
        observeViewModel()
        setupTakePhotoButton()
        unpackArguments()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            PhotoActivity.getPhotoPath(data)?.also { path ->
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
                viewModel.uploadPhoto(File(path))
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this)[WardrobeGalleryViewModel::class.java]
    }

    private fun observeViewModel() {
        viewModel.viewState
            .observe(viewLifecycleOwner) {
                progressBar isVisibleWhen isLoading
                layoutContent isVisibleWhen !isLoading
                loadImages(photoUrls)
            }
        viewModel.errorState
            .observeEvent(viewLifecycleOwner) {
                showMessage(it)
                navigateUp()
            }
    }

    private fun loadImages(photoUrls: List<String>) {
        photoUrls.forEachIndexed { index, url ->
            imageViews[index]
                ?.also { it.loadPosterImage(url) }
                ?.apply { setOnClickListener { openViewer(index, this) } }
        }
    }

    private fun openViewer(startPosition: Int, target: ImageView) {
        viewer = StfalconImageViewer.Builder(context, viewModel.photoUrls, ImageLoader<String> { imageView, image -> imageView?.loadImage(image) })
            .withStartPosition(startPosition)
            .withTransitionFrom(target)
            .withImageChangeListener { viewer.updateTransitionImage(imageViews[it]) }
            .show()
    }

    private fun setupTakePhotoButton() {
        btnTakePhoto.setOnClickListener {
            val intent = Intent(it.context, PhotoActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO)
        }
    }

    private fun unpackArguments() {
        viewModel.wardrobeId = arguments?.getString(KEY_WARDROBE_ID)
    }

    private fun ImageView.loadPosterImage(url: String) {
        show()
        background = context.getDrawableCompat(R.drawable.ic_wardrobe)
        loadImage(url)
    }

    private fun ImageView.loadImage(url: String?) = Picasso.get().load(url).fit().into(this)

    private fun Context.getDrawableCompat(@DrawableRes drawableRes: Int): Drawable? = AppCompatResources.getDrawable(this, drawableRes)

    companion object {

        fun createExtras(wardrobeId: String): Bundle = Bundle().apply {
            putString(KEY_WARDROBE_ID, wardrobeId)
        }
    }
}