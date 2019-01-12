package com.wardrobes.porenut.ui.viewer.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.extension.show
import com.wardrobes.porenut.ui.viewer.model.Model
import com.wardrobes.porenut.ui.viewer.model.ObjModel
import kotlinx.android.synthetic.main.activity_model.*
import java.io.InputStream

private const val READ_PERMISSION_REQUEST = 100
private const val OPEN_DOCUMENT_REQUEST = 101

class ModelActivity : AppCompatActivity() {

    private var modelView: ModelSurfaceView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_model)

        if (intent.data != null && savedInstanceState == null) {
            beginLoadModel(intent.data)
        }
    }

    override fun onPause() {
        super.onPause()
        modelView?.onPause()
    }

    override fun onResume() {
        super.onResume()
        modelView?.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        checkReadPermissionThenOpen()
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            READ_PERMISSION_REQUEST -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                beginOpenModel()
            } else {
                Toast.makeText(this, "Permission failed", Toast.LENGTH_SHORT).show()
            }
            else -> {
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == OPEN_DOCUMENT_REQUEST && resultCode == RESULT_OK && data?.data != null) {
            val uri = data.data
            grantUriPermission(packageName, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            beginLoadModel(uri)
        }
    }

    private fun checkReadPermissionThenOpen() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
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

    private fun beginLoadModel(uri: Uri?) {
        progressBar.show()
        ModelLoadTask().execute(uri)
    }

    private inner class ModelLoadTask : AsyncTask<Uri, Int, Model>() {
        override fun doInBackground(vararg file: Uri): Model? {
            var stream: InputStream? = null
            try {
                val uri = file[0]
                val cr = applicationContext.contentResolver
                stream = cr.openInputStream(uri)
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
//            model?.also { setCurrentModel(it) }
        }
    }
}
