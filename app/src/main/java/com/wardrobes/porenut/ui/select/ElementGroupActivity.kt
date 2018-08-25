package com.wardrobes.porenut.ui.select

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
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