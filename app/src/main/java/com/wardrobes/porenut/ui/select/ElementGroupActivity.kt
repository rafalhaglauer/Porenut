package com.wardrobes.porenut.ui.select

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.element.elementId
import com.wardrobes.porenut.ui.extension.finishWithResult
import com.wardrobes.porenut.ui.extension.setVisible
import com.wardrobes.porenut.ui.extension.showMessage
import com.wardrobes.porenut.ui.wardrobe.detail.ElementGroupAdapter
import com.wardrobes.porenut.ui.wardrobe.detail.ElementGroupViewModel
import com.wardrobes.porenut.ui.wardrobe.detail.ElementViewEntity
import kotlinx.android.synthetic.main.view_list.*

class ElementGroupActivity : AppCompatActivity() {
    private lateinit var elementGroupViewModel: ElementGroupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_list)
        elementGroupViewModel = ViewModelProviders.of(this)[ElementGroupViewModel::class.java]
        observeViewModel()

    }

    private fun observeViewModel() {
        elementGroupViewModel.viewState
            .observe(this, Observer {
                progress.setVisible(it!!.isLoading)
                bind(it.viewEntities)
                showMessage(it.errorMessage)
            })
    }

    private fun bind(viewEntities: List<ElementViewEntity>) {
        contentLayout.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                ).apply {
                    ContextCompat.getDrawable(context, R.drawable.divider)?.also { setDrawable(it) }
                })
            adapter = ElementGroupAdapter(viewEntities) {
                elementGroupViewModel.selectElement(it)
                finishWithResult(Activity.RESULT_OK) {
                    elementId = elementGroupViewModel.selectedElementId
                }
            }
        }
    }
}