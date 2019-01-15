package com.wardrobes.porenut.ui.wardrobe.detail

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.extension.inflate
import kotlinx.android.synthetic.main.view_wardrobe_gallery.view.*

class WardrobeGalleryView(context: Context?, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    var imageLoader: ((ImageView, Poster?) -> Unit)? = null
    var onPosterClick: ((Int, ImageView) -> Unit)? = null

    val imageViews by lazy {
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

    init {
        inflate(R.layout.view_wardrobe_gallery, attachToRoot = true)
    }

    fun loadImages() {
        imageViews.values.forEachIndexed { index, imageView ->
            imageLoader?.invoke(imageView, Demo.posters.getOrNull(index))
            imageView.setOnClickListener { onPosterClick?.invoke(index, imageView) }
        }
    }
}