package com.wardrobes.porenut.ui.wardrobe.manage

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import com.wardrobes.porenut.R
import com.wardrobes.porenut.api.extension.fetchStateFullModel
import com.wardrobes.porenut.data.wardrobe.WardrobeRestRepository
import com.wardrobes.porenut.domain.Wardrobe
import com.wardrobes.porenut.ui.extension.float
import com.wardrobes.porenut.ui.extension.string
import kotlinx.android.synthetic.main.activity_manage_wardrobe.*

class ManageWardrobeActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_wardrobe)

        btnPositive.setOnClickListener {
            WardrobeRestRepository.add(Wardrobe(
                    width = txtWardrobeWidth.float(),
                    height = txtWardrobeHeight.float(),
                    depth = txtWardrobeDepth.float(),
                    symbol = txtWardrobeSymbol.string(),
                    type = if (checkBoxIsWardrobeHanging.isChecked) Wardrobe.Type.HANGING else Wardrobe.Type.STANDING
            )).fetchStateFullModel(
                    onLoading = { showMessage("Loading") },
                    onSuccess = { showMessage("Added") },
                    onError = { if (it != null) showMessage(it) else showMessage("Error occurred!") }
            )
            finish()
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
