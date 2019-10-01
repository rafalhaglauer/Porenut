//package com.wardrobes.porenut.ui.wardrobe.model
//
//import android.view.MotionEvent
//import com.wardrobes.porenut.ui.wardrobe.dashboard.WardrobeModelStorage
//import min3d.core.RendererActivity
//import min3d.vos.Light
//
//class WardrobeModelActivity : RendererActivity() {
//    private var modelController: ModelController? = null
//
//    override fun initScene() {
//        WardrobeModelStorage.wardrobeModel?.also { model ->
//            model.scale().apply { z = 0.4F; x = 0.4F; y = 0.4F }
//            scene.lights().add(Light())
//            scene.addChild(model)
//            modelController = ModelController(model)
//        }
//    }
//
//    override fun onTouchEvent(event: MotionEvent): Boolean = if (modelController?.handleEvent(event) == true) true else super.onTouchEvent(event)
//
//}
// TODO