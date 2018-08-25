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
import com.wardrobes.porenut.ui.extension.finishWithResult
import com.wardrobes.porenut.ui.extension.setVisible
import com.wardrobes.porenut.ui.extension.showMessage
import com.wardrobes.porenut.ui.relative.composition.RelativeCompositionGroupAdapter
import com.wardrobes.porenut.ui.relative.composition.RelativeCompositionGroupViewModel
import com.wardrobes.porenut.ui.relativeCompositionId
import kotlinx.android.synthetic.main.view_list.*

class RelativeCompositionGroupActivity : AppCompatActivity() {

    private lateinit var viewModel: RelativeCompositionGroupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_list)
        viewModel = ViewModelProviders.of(this)[RelativeCompositionGroupViewModel::class.java]
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.viewState
            .observe(this, Observer {
                it?.also {
                    progress.setVisible(it.isLoading)
                    bind(it.compositions)
                    showMessage(it.errorMessage)
                }
            })
        viewModel.startObserving()
    }

    private fun bind(compositions: List<String>) {
        contentLayout.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL).apply {
                ContextCompat.getDrawable(context, R.drawable.divider)?.also { setDrawable(it) }
            })
            adapter = RelativeCompositionGroupAdapter(compositions) {
                finishWithResult(Activity.RESULT_OK) {
                    relativeCompositionId = viewModel.getRelativeCompositionId(it)
                }
            }
        }
    }
}