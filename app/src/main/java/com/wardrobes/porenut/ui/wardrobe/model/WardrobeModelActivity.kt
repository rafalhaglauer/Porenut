package com.wardrobes.porenut.ui.wardrobe.model

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.MotionEvent
import com.wardrobes.porenut.domain.CustomObjParser
import min3d.core.RendererActivity
import min3d.vos.Light

private const val KEY_WARDROBE_MODEL = "key-wardrobe-model"

class WardrobeModelActivity : RendererActivity() {
    private var modelController: ModelController? = null

    override fun initScene() {
        intent.getParcelableExtra<Uri>(KEY_WARDROBE_MODEL)?.also { uri ->
            contentResolver.openInputStream(uri)?.let { CustomObjParser.parse(it) }
                ?.apply {
                    scale().apply { z = 0.4F; x = 0.4F; y = 0.4F }
                }?.also {
                    scene.lights().add(Light())
                    scene.addChild(it)
                    modelController = ModelController(it)
                }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean = if (modelController?.handleEvent(event) == true) true else super.onTouchEvent(event)

    companion object {

        fun launchActivity(context: Context, model: Uri) {
            context.startActivity(Intent(context, WardrobeModelActivity::class.java).apply { putExtra(KEY_WARDROBE_MODEL, model) })
        }
    }
}