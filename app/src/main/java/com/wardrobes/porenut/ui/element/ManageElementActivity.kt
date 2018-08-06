package com.wardrobes.porenut.ui.element

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.extension.*
import com.wardrobes.porenut.ui.wardrobe.detail.ElementViewEntity
import com.wardrobes.porenut.ui.wardrobe.manage.requestType
import com.wardrobes.porenut.ui.wardrobe.manage.wardrobeId
import kotlinx.android.synthetic.main.activity_manage_element.*

class ManageElementActivity : AppCompatActivity() {
    private lateinit var manageElementViewModel: ManageElementViewModel

    private var elementName: String
        get() = txtElementName.string()
        set(value) {
            txtElementName.setText(value)
        }
    private var elementLength: String
        get() = txtElementLength.string()
        set(value) {
            txtElementLength.setText(value)
        }
    private var elementWidth: String
        get() = txtElementWidth.string()
        set(value) {
            txtElementWidth.setText(value)
        }
    private var elementHeight: String
        get() = txtElementHeight.string()
        set(value) {
            txtElementHeight.setText(value)
        }

    override

    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_element)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_close)
        }
        manageElementViewModel = ViewModelProviders.of(this)[ManageElementViewModel::class.java]
        observeViewModel()
        manageElementViewModel.wardrobeId = intent.wardrobeId
        manageElementViewModel.elementId = intent.elementId
        manageElementViewModel.requestType = intent.requestType
        btnManageElement.setOnClickListener {
            it.hideKeyboard()
            manageElementViewModel.manage(build())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?) =
            when (item?.itemId) {
                android.R.id.home -> onBackPressed().run { true }
                else -> super.onOptionsItemSelected(item)
            }

    private fun observeViewModel() {
        manageElementViewModel.viewState.observe(this, Observer {
            val result = it!!.resultType
            if (result == null) {
                progress.setVisible(it.isLoading)
                layoutContent.setVisible(!it.isLoading)
                showMessage(it.errorMessage)
                txtManageElement.text = getString(it.btnTextMessage)
                it.viewEntity?.also { bind(it) }
                if (it.disableEveryFieldExceptName) listOf(txtElementLength, txtElementWidth, txtElementHeight).forEach { it.isEnabled = false }

            } else {
                finishWithResult(result.value) {
                    elementId = manageElementViewModel.elementId
                }
            }
        })
    }

    private fun bind(elementViewEntity: ElementViewEntity) {
        with(elementViewEntity) {
            elementName = name
            elementLength = length
            elementWidth = width
            elementHeight = height
        }
    }

    private fun build() = ElementViewEntity(
            name = elementName,
            length = elementLength,
            width = elementWidth,
            height = elementHeight
    )
}