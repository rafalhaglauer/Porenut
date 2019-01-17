package com.wardrobes.porenut.ui.element.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.element.detail.ElementViewEntity
import com.wardrobes.porenut.ui.extension.*
import kotlinx.android.synthetic.main.fragment_manage_element.*

private const val KEY_WARDROBE_ID = "key-wardrobe-id"
private const val KEY_ELEMENT_ID = "key-element-id"

class ManageElementFragment : Fragment() {
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        container?.inflate(R.layout.fragment_manage_element)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModel()
        observeViewModel()
        setupListener()
        unpackArguments()
    }

    private fun setupViewModel() {
        manageElementViewModel = ViewModelProviders.of(this)[ManageElementViewModel::class.java]
    }

    private fun observeViewModel() {
        manageElementViewModel.viewState.observe(viewLifecycleOwner) {
            progress isVisibleWhen isLoading
            layoutContent isVisibleWhen !isLoading
            txtManageElement.text = getString(btnTextMessage)
            bind(viewEntity)
        }
        manageElementViewModel.errorStateEvent.observeEvent(viewLifecycleOwner) {
            showMessage(it)
        }
        manageElementViewModel.navigateBackEvent.observeEvent(viewLifecycleOwner) {
            navigateUp()
        }
    }

    private fun setupListener() {
        btnManageElement.setOnClickListener {
            it.hideKeyboard()
            manageElementViewModel.manage(build())
        }
    }

    private fun unpackArguments() {
        arguments?.run {
            manageElementViewModel.wardrobeId = getLong(KEY_WARDROBE_ID)
            manageElementViewModel.elementId = getLong(KEY_ELEMENT_ID).takeIf { containsKey(KEY_ELEMENT_ID) }
        }
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

    companion object {

        fun createExtras(wardrobeId: Long, elementId: Long? = null): Bundle = Bundle().apply {
            putLong(KEY_WARDROBE_ID, wardrobeId)
            elementId?.also { putLong(KEY_ELEMENT_ID, it) }
        }
    }
}