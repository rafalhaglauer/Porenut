package com.wardrobes.porenut.ui.drilling.relative.manage

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.extension.*
import com.wardrobes.porenut.ui.vo.relativeCompositionId
import com.wardrobes.porenut.ui.vo.relativeDrillingId
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
        btnManageComposition.setOnClickListener {
            it.hideKeyboard()
            viewModel.manage(
                name = txtName.string(),
                xOffset = viewXOffset.offset,
                yOffset = viewYOffset.offset,
                depth = txtDepth.float(),
                diameter = txtDiameter.float()
            )
        }
        btnDeleteRelativeDrilling.setOnClickListener {
            viewModel.delete()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?) =
        when (item?.itemId) {
            android.R.id.home -> onBackPressed().run { true }
            else -> super.onOptionsItemSelected(item)
        }

    private fun observeViewModel() {
        viewModel.viewState.observe(this) {
            result?.also { finishWithResult(it.value) }
            progress.isVisibleWhen(isLoading)
            layoutContent.isVisibleWhen(!isLoading)
            btnDeleteRelativeDrilling.isVisibleWhen(isDeleteButtonVisible)
            viewEntity?.also { bind(it) }
            showMessage(errorMessage)
            txtManageRelativeDrilling.text = getString(manageText)
        }
        viewModel.relativeCompositionId = intent.relativeCompositionId
        viewModel.relativeDrillingId = intent.relativeDrillingId
    }

    private fun bind(viewEntity: RelativeDrillingViewEntity) {
        txtName.setText(viewEntity.name)
        txtDepth.setText(viewEntity.depth)
        txtDiameter.setText(viewEntity.diameter)
        viewXOffset.offset = viewEntity.xOffset
        viewYOffset.offset = viewEntity.yOffset
    }
}
