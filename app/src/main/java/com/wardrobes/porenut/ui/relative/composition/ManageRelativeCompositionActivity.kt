package com.wardrobes.porenut.ui.relative.composition

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.extension.*
import kotlinx.android.synthetic.main.activity_manage_relative_composition.*

class ManageRelativeCompositionActivity : AppCompatActivity() {
    private lateinit var viewModel: ManageRelativeCompositionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_relative_composition)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_close)
        }
        viewModel = ViewModelProviders.of(this)[ManageRelativeCompositionViewModel::class.java]
        observeViewModel()
        btnManageRelativeComposition.setOnClickListener {
            it.hideKeyboard()
            viewModel.add(
                name = txtRelativeCompositionName.string(),
                xReferenceType = spinnerXReferenceLength.selectedItem as String,
                yReferenceType = spinnerYReferenceLength.selectedItem as String
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?) =
        when (item?.itemId) {
            android.R.id.home -> onBackPressed().run { true }
            else -> super.onOptionsItemSelected(item)
        }

    private fun observeViewModel() {
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
