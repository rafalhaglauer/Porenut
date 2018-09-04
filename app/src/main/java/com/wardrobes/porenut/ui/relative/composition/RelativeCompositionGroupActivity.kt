package com.wardrobes.porenut.ui.relative.composition

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.extension.launchActivity
import com.wardrobes.porenut.ui.extension.setVisible
import com.wardrobes.porenut.ui.extension.showMessage
import com.wardrobes.porenut.ui.relative.drilling.RelativeDrillingGroupActivity
import com.wardrobes.porenut.ui.vo.Result
import com.wardrobes.porenut.ui.vo.relativeCompositionId
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
        layoutContent.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                ).apply {
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