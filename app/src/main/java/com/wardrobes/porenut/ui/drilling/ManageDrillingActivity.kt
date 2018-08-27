package com.wardrobes.porenut.ui.drilling

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.element.DrillingViewEntity
import com.wardrobes.porenut.ui.element.elementId
import com.wardrobes.porenut.ui.extension.*
import com.wardrobes.porenut.ui.wardrobe.Result
import com.wardrobes.porenut.ui.wardrobe.manage.drillingId
import kotlinx.android.synthetic.main.activity_manage_drilling.*

class ManageDrillingActivity : AppCompatActivity() {
    private lateinit var viewModel: ManageDrillingViewModel

    private var xPosition: String
        get() = txtXPosition.string()
        set(value) {
            txtXPosition.setText(value)
        }
    private var yPosition: String
        get() = txtYPosition.string()
        set(value) {
            txtYPosition.setText(value)
        }
    private var diameter: String
        get() = txtDiameter.string()
        set(value) {
            txtDiameter.setText(value)
        }
    private var depth: String
        get() = txtDepth.string()
        set(value) {
            txtDepth.setText(value)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_drilling)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_close)
        }
        viewModel = ViewModelProviders.of(this)[ManageDrillingViewModel::class.java]
        observeViewModel()
        viewModel.elementId = intent.elementId
        viewModel.drillingId = intent.drillingId
        btnManageDrilling.setOnClickListener {
            it.hideKeyboard()
            viewModel.manage(build())
        }
        btnAction.inflate(R.menu.menu_drilling_details)
        btnAction.setOnActionSelectedListener {
            when (it.id) {
                R.id.action_delete_drilling -> viewModel.delete()
                R.id.action_copy_drilling -> viewModel.copy(build())
            }
            false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?) =
        when (item?.itemId) {
            android.R.id.home -> onBackPressed().run { true }
            else -> super.onOptionsItemSelected(item)
        }

    private fun observeViewModel() {
        viewModel.viewState.observe(this, Observer {
            if (it!!.result != Result.MODIFIED) {
                it.result?.also { setResult(it.value) }
                progress.setVisible(it.isLoading)
                layoutContent.setVisible(!it.isLoading)
                showMessage(it.errorMessage)
                it.viewEntity?.also { bind(it) }
                txtManageDrilling.setText(it.btnTextMessage)
                btnAction.setVisible(it.isManageBtnVisible)
            } else {
                finishWithResult(it.result!!.value)
            }
        })
    }

    private fun bind(drillingViewEntity: DrillingViewEntity) {
        drillingViewEntity.also {
            xPosition = it.xPosition
            yPosition = it.yPosition
            diameter = it.diameter
            depth = it.depth
        }
    }

    private fun build() = DrillingViewEntity(
        xPosition = xPosition,
        yPosition = yPosition,
        diameter = diameter,
        depth = depth
    )
}