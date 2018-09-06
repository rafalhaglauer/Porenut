package com.wardrobes.porenut.ui.drilling.relative.manage

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.extension.*
import com.wardrobes.porenut.ui.vo.relativeCompositionId
import kotlinx.android.synthetic.main.activity_manage_relative_drilling.*

class ManageRelativeDrillingActivity : AppCompatActivity() {
    private lateinit var viewModel: ManageRelativeDrillingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_relative_drilling)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_close)
            title = getString(R.string.l_add_drilling)
        }
        viewModel = ViewModelProviders.of(this)[ManageRelativeDrillingViewModel::class.java]
        observeViewModel()
        btnManageRelativeDrilling.setOnClickListener {
            it.hideKeyboard()
            viewModel.add(
                name = txtName.string(),
                xOffset = viewXOffset.compositeOffset,
                yOffset = viewYOffset.compositeOffset,
                depth = txtDepth.float(),
                diameter = txtDiameter.float()
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?) =
        when (item?.itemId) {
            android.R.id.home -> onBackPressed().run { true }
            else -> super.onOptionsItemSelected(item)
        }

    private fun observeViewModel() {
        viewModel.relativeCompositionId = intent.relativeCompositionId
        viewModel.viewState.observe(this, Observer {
            it!!.also {
                it.result?.also { finishWithResult(it.value) }
                progress.setVisible(it.isLoading)
                layoutContent.setVisible(!it.isLoading)
                showMessage(it.errorMessage)
            }
        })
    }
}
