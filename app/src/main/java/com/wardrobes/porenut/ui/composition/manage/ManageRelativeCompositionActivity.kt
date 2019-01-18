package com.wardrobes.porenut.ui.composition.manage

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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
            viewModel.add(name = txtRelativeCompositionName.string())
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
                progress.isVisibleWhen(it.isLoading)
                layoutContent.isVisibleWhen(!it.isLoading)
                showMessage(it.errorMessage)
            }
        })
    }
}
