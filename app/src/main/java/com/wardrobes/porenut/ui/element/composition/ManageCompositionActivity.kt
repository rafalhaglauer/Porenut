package com.wardrobes.porenut.ui.element.composition

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.extension.finishWithResult
import com.wardrobes.porenut.ui.extension.setVisible
import com.wardrobes.porenut.ui.vo.elementId
import com.wardrobes.porenut.ui.vo.wardrobeId
import kotlinx.android.synthetic.main.activity_manage_reference_element_composition.*

class ManageCompositionActivity : AppCompatActivity() {
    private lateinit var viewModel: ManageCompositionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_reference_element_composition)
        btnManageRelativeDrilling.setOnClickListener {
            viewModel.add(
                drillingCompositionName = spinnerRelativeDrillingComposition.selectedItem as String,
                xOffset = viewXOffset.compositeOffset,
                yOffset = viewYOffset.compositeOffset,
                referenceElementName = spinnerElementReference.selectedItem as String,
                xReferenceLengthName = spinnerXReferenceLength.selectedItem as String,
                yReferenceLengthName = spinnerYReferenceLength.selectedItem as String
            )
        }
        viewModel = ViewModelProviders.of(this)[ManageCompositionViewModel::class.java]
        viewModel.viewState.observe(this, Observer {
            it!!.result?.also {
                finishWithResult(it.value)
            }
            progress.setVisible(it.isLoading)
            layoutContent.setVisible(!it.isLoading)
            spinnerElementReference.adapter =
                    ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, it.elementNames)
            spinnerRelativeDrillingComposition.adapter = ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                it.compositionNames
            )
        })
        viewModel.elementId = intent.elementId
        viewModel.wardrobeId = intent.wardrobeId
    }
}