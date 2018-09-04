package com.wardrobes.porenut.ui.element

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.extension.*
import com.wardrobes.porenut.ui.v2.element.ElementViewEntity
import com.wardrobes.porenut.ui.vo.elementId
import com.wardrobes.porenut.ui.vo.requestType
import com.wardrobes.porenut.ui.vo.wardrobeId
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
            android.R.id.home -> finish().run { true }
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
                if (it.disableEveryFieldExceptName) listOf(
                    txtElementLength,
                    txtElementWidth,
                    txtElementHeight
                ).forEach { it.isEnabled = false }

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