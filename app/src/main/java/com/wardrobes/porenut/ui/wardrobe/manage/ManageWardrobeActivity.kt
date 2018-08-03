package com.wardrobes.porenut.ui.wardrobe.manage

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wardrobes.porenut.R
import com.wardrobes.porenut.domain.UNDEFINED_ID
import com.wardrobes.porenut.ui.extension.setVisible
import com.wardrobes.porenut.ui.extension.showMessage
import com.wardrobes.porenut.ui.extension.string
import kotlinx.android.synthetic.main.activity_manage_wardrobe.*

private const val KEY_WARDROBE_ID = "key-wardrobe-id"

class ManageWardrobeActivity : AppCompatActivity() {
    private lateinit var manageWardrobeViewModel: ManageWardrobeViewModel

    companion object {
        private var Intent.wardrobeId: Long
            set(value) {
                putExtra(KEY_WARDROBE_ID, value)
            }
            get() = getLongExtra(KEY_WARDROBE_ID, UNDEFINED_ID)

        fun launch(activity: Activity, wardrobeId: Long = UNDEFINED_ID) {
            val intent = Intent(activity, ManageWardrobeActivity::class.java)
            intent.wardrobeId = wardrobeId
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_wardrobe)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        manageWardrobeViewModel = ViewModelProviders.of(this)[ManageWardrobeViewModel::class.java]
        observeViewModel()

        manageWardrobeViewModel.wardrobeId = intent.wardrobeId

        btnManageWardrobe.setOnClickListener { manageWardrobeViewModel.viewEntity = build() }
    }

    private fun observeViewModel() {
        manageWardrobeViewModel.viewState
                .observe(this, Observer {
                    progress.setVisible(it!!.isLoading)
                    layoutContent.setVisible(!it.isLoading)
                    bind(it.viewEntity)
                    showMessage(it.errorMessage)
                    showMessage(it.successMessage)
                    if (it.shouldFinish) finish()
                })
    }

    private fun bind(viewEntity: ManageWardrobeViewEntity) {
        with(viewEntity) {
            txtWardrobeSymbol.setText(symbol)
            txtWardrobeWidth.setText(width)
            txtWardrobeHeight.setText(height)
            txtWardrobeDepth.setText(depth)
            checkBoxIsWardrobeHanging.isChecked = isHanging
        }
    }

    private fun build(): ManageWardrobeViewEntity = ManageWardrobeViewEntity(
            symbol = txtWardrobeSymbol.string(),
            width = txtWardrobeWidth.string(),
            height = txtWardrobeHeight.string(),
            depth = txtWardrobeDepth.string(),
            isHanging = checkBoxIsWardrobeHanging.isChecked
    )
}
