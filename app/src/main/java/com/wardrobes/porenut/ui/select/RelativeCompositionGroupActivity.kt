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
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                ).apply {
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