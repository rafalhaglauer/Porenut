package com.wardrobes.porenut.ui.photo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.common.extension.begone
import com.wardrobes.porenut.ui.common.extension.isVisibleWhen
import com.wardrobes.porenut.ui.common.extension.show
import com.wardrobes.porenut.ui.common.onProgressChanged
import io.fotoapparat.Fotoapparat
import io.fotoapparat.configuration.CameraConfiguration
import io.fotoapparat.configuration.UpdateConfiguration
import io.fotoapparat.parameter.Flash
import io.fotoapparat.parameter.Zoom
import io.fotoapparat.result.BitmapPhoto
import io.fotoapparat.result.PhotoResult
import io.fotoapparat.selector.*
import kotlinx.android.synthetic.main.activity_photo.*
import java.io.File
import java.util.*
import kotlin.math.roundToInt

private const val GALLERY_DIRECTORY = "gallery"
private const val WARDROBE_PHOTO_FILENAME_FORMAT = "wardrobe_%s.jpg"
private const val KEY_PHOTO_PATH = "key-photo-path"

class PhotoActivity : AppCompatActivity() {

    private val permissionsDelegate = PermissionsDelegate(this)

    private var permissionsGranted: Boolean = false
    private var photoResult: PhotoResult? = null

    private lateinit var camera: Fotoapparat
    private lateinit var cameraZoom: Zoom.VariableZoom

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)
        checkPermissionState()
        setupCamera()
        setupListeners()
    }

    private fun checkPermissionState() {
        permissionsGranted = permissionsDelegate.hasCameraPermission().also { granted ->
            viewCamera isVisibleWhen granted
            if (!granted) permissionsDelegate.requestCameraPermission()
        }
    }

    private fun setupCamera() {
        camera = Fotoapparat(
            context = this,
            view = viewCamera,
            focusView = viewFocus,
            lensPosition = Camera.Back.lensPosition,
            cameraConfiguration = Camera.Back.configuration
        )
    }

    private fun setupListeners() {
        imgCapture.setOnClickListener { takePicture() }
        switchTorch.setOnCheckedChangeListener(toggleFlash())
        btnCancel.setOnClickListener { hideResult() }
        btnSave.setOnClickListener { photoResult?.also { result -> saveResult(result) } }
    }

    private fun takePicture() {
        photoResult = camera
            .autoFocus()
            .takePicture()
            .also { result ->
                //  TODO set scaled? scaled(scaleFactor = 0.9f)
                result.toBitmap().whenAvailable { photo -> photo?.let { showResult(it) } }
            }
    }

    private fun toggleFlash(): (CompoundButton, Boolean) -> Unit = { _, isChecked ->
        camera.updateConfiguration(
            UpdateConfiguration(
                flashMode = if (isChecked) {
                    firstAvailable(torch(), off())
                } else {
                    off()
                }
            )
        )
    }

    override fun onStart() {
        super.onStart()
        if (permissionsGranted) {
            camera.start()
            adjustViewsVisibility()
        }
    }

    override fun onStop() {
        super.onStop()
        if (permissionsGranted) {
            camera.stop()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (permissionsDelegate.resultGranted(requestCode, permissions, grantResults)) {
            permissionsGranted = true
            camera.start()
            adjustViewsVisibility()
            viewCamera.show()
        } else {
            finish()
        }
    }

    private fun adjustViewsVisibility() {
        camera.getCapabilities()
            .whenAvailable { capabilities ->
                capabilities
                    ?.let {
                        (it.zoom as? Zoom.VariableZoom)
                            ?.let { zoom -> setupZoom(zoom) }
                            ?: run { seekBarZoom.begone() }

                        switchTorch isVisibleWhen it.flashModes.contains(Flash.Torch)
                    }
            }
    }

    private fun setupZoom(zoom: Zoom.VariableZoom) {
        cameraZoom = zoom
        seekBarZoom.apply {
            max = zoom.maxZoom
            show()
            onProgressChanged { updateZoom(progress) }
        }
        updateZoom(0)
    }

    private fun updateZoom(progress: Int) {
        camera.setZoom(progress.toFloat() / seekBarZoom.max)
        val value = cameraZoom.zoomRatios[progress]
        val roundedValue = ((value.toFloat()) / 10).roundToInt().toFloat() / 10
        txtZoomLvl.text = String.format("%.1f ×", roundedValue)
    }

    private fun saveResult(result: PhotoResult) {
        File(getExternalFilesDir(GALLERY_DIRECTORY), String.format(WARDROBE_PHOTO_FILENAME_FORMAT, "${Date()}")).also {
            result.saveToFile(it)
            setResult(Activity.RESULT_OK, Intent().apply { putExtra(KEY_PHOTO_PATH, it.path) })
        }
        finish()
    }

    private fun showResult(photo: BitmapPhoto) {
        imgResult.setImageBitmap(photo.bitmap)
        imgResult.rotation = (-photo.rotationDegrees).toFloat()
        layoutResult.show()
        viewCamera.begone()
        layoutTools.begone()
    }

    private fun hideResult() {
        viewCamera.show()
        layoutTools.show()
        layoutResult.begone()
        photoResult = null
    }

    companion object {

        fun getPhotoPath(data: Intent?): String? = data?.getStringExtra(KEY_PHOTO_PATH)
    }
}

private sealed class Camera(
    val lensPosition: LensPositionSelector,
    val configuration: CameraConfiguration
) {

    object Back : Camera(
        lensPosition = back(),
        configuration = CameraConfiguration(
            previewResolution = firstAvailable(wideRatio(highestResolution()), standardRatio(highestResolution())),
            previewFpsRange = highestFps(),
            flashMode = off(),
            focusMode = firstAvailable(continuousFocusPicture(), autoFocus())
        )
    )
}