package com.wardrobes.porenut.ui.wardrobe.model

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.common.extension.inflate
import com.wardrobes.porenut.ui.common.extension.showMessage
import kotlinx.android.synthetic.main.fragment_wardrobe_model.*

private const val READ_PERMISSION_REQUEST = 100
private const val OPEN_DOCUMENT_REQUEST = 101

class WardrobeModelFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        container?.inflate(R.layout.fragment_wardrobe_model)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btnAddModel.setOnClickListener {
            checkReadPermissionThenOpen()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == READ_PERMISSION_REQUEST && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            beginOpenModel()
        } else {
            showMessage("Permission failed")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == OPEN_DOCUMENT_REQUEST && resultCode == Activity.RESULT_OK && data?.data != null) {
            data.data?.also { beginLoadModel(it) }
        }
    }

    private fun checkReadPermissionThenOpen() {
        if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), READ_PERMISSION_REQUEST)
        } else {
            beginOpenModel()
        }
    }

    private fun beginOpenModel() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "*/*"
        startActivityForResult(intent, OPEN_DOCUMENT_REQUEST)
    }

    private fun beginLoadModel(uri: Uri) {
        context?.also { WardrobeModelActivity.launchActivity(it, uri) }
    }
}
