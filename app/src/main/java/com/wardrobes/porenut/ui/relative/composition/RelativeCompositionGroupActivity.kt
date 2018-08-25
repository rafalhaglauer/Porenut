package com.wardrobes.porenut.ui.relative.composition

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.extension.launchActivity
import com.wardrobes.porenut.ui.extension.setVisible
import com.wardrobes.porenut.ui.extension.showMessage
import com.wardrobes.porenut.ui.relative.drilling.RelativeDrillingGroupActivity
import com.wardrobes.porenut.ui.relativeCompositionId
import com.wardrobes.porenut.ui.wardrobe.Result
import kotlinx.android.synthetic.main.view_list.*

private const val ADD_RELATIVE_COMPOSITION_REQUEST_CODE = 1

class RelativeCompositionGroupActivity : AppCompatActivity() {

    private lateinit var viewModel: RelativeCompositionGroupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_list)
        viewModel = ViewModelProviders.of(this)[RelativeCompositionGroupViewModel::class.java]
        observeViewModel()
        btnAction.show()
        btnAction.setOnClickListener {
            launchActivity<ManageRelativeCompositionActivity>(ADD_RELATIVE_COMPOSITION_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Result.ADDED.value) viewModel.refresh()
        super.onActivityResult(requestCode, resultCode, data)
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
                launchActivity<RelativeDrillingGroupActivity> {
                    relativeCompositionId = viewModel.getRelativeCompositionId(it)
                }
            }
        }
    }
}