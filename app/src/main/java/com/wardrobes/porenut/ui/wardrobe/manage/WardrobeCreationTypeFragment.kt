package com.wardrobes.porenut.ui.wardrobe.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.common.extension.inflate
import com.wardrobes.porenut.ui.common.extension.navigateTo
import kotlinx.android.synthetic.main.fragment_wardrobe_creation_type.*

class WardrobeCreationTypeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        container?.inflate(R.layout.fragment_wardrobe_creation_type)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        btnGenerateWardrobe.setOnClickListener {
            navigateTo(R.id.wardrobeCreationTypeFragmentToSelectWardrobePatternFragment)
        }
        btnCreateCustomWardrobe.setOnClickListener {
            navigateTo(R.id.wardrobeCreationTypeFragmentToManageWardrobeFragment)
        }
    }

}