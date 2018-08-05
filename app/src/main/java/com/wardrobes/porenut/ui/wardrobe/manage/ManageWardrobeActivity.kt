package com.wardrobes.porenut.ui.wardrobe.manage

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.extension.*
import kotlinx.android.synthetic.main.activity_manage_wardrobe.*

class ManageWardrobeActivity : AppCompatActivity() {
    private lateinit var manageWardrobeViewModel: ManageWardrobeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_wardrobe)

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_close)
        }

        manageWardrobeViewModel = ViewModelProviders.of(this)[ManageWardrobeViewModel::class.java]
        observeViewModel()

        manageWardrobeViewModel.wardrobeId = intent.wardrobeId
        manageWardrobeViewModel.creationType = intent.creationType
        manageWardrobeViewModel.requestType = intent.requestType

        btnManageWardrobe.setOnClickListener {
            it.hideKeyboard()
            manageWardrobeViewModel.viewEntity = build()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?) =
            when (item?.itemId) {
                android.R.id.home -> onBackPressed().run { true }
                else -> super.onOptionsItemSelected(item)
            }

    private fun observeViewModel() {
        manageWardrobeViewModel.viewState
                .observe(this, Observer {
                    val resultType = it!!.resultType
                    if (resultType == null) {
                        progress.setVisible(it.isLoading)
                        layoutContent.setVisible(!it.isLoading)
                        bind(it.viewEntity)
                        txtManageWardrobe.text = getString(it.btnActionText)
                        showMessage(it.errorMessage)
                        if (it.disableEveryFieldExceptSymbol) listOf(txtWardrobeWidth, txtWardrobeHeight, txtWardrobeDepth, checkBoxIsWardrobeHanging).forEach { it.isEnabled = false }
                    } else {
                        finishWithResult(resultType.value) {
                            wardrobeId = manageWardrobeViewModel.wardrobeId
                        }
                    }
                })
    }

    private fun bind(viewEntity: ManageWardrobeViewEntity) {
        with(viewEntity) {
            txtWardrobeSymbol.setText(symbol)
            txtWardrobeWidth.setText(width)
            txtWardrobeHeight.setText(height)
            txtWardrobeDepth.setText(depth)
            checkBoxIsWardrobeHanging.isChecked = isHanging
        }
    }

    private fun build(): ManageWardrobeViewEntity = ManageWardrobeViewEntity(
            symbol = txtWardrobeSymbol.string(),
            width = txtWardrobeWidth.string(),
            height = txtWardrobeHeight.string(),
            depth = txtWardrobeDepth.string(),
            isHanging = checkBoxIsWardrobeHanging.isChecked
    )
}
