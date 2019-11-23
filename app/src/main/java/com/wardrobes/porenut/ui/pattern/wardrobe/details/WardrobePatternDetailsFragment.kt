package com.wardrobes.porenut.ui.pattern.wardrobe.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.common.extension.navigateTo
import com.wardrobes.porenut.ui.common.extension.setTitle
import com.wardrobes.porenut.ui.pattern.element.group.ElementPatternGroupFragment
import com.wardrobes.porenut.ui.pattern.wardrobe.manage.ManageWardrobePatternFragment
import kotlinx.android.synthetic.main.fragment_wardrobe_pattern_details.*

private const val KEY_WARDROBE_PATTERN_ID = "key-wardrobe-pattern-id"

class WardrobePatternDetailsFragment : Fragment() {

    private val patternId: String
        get() = arguments?.getString(KEY_WARDROBE_PATTERN_ID)!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_wardrobe_pattern_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setTitle(patternId)
        btnManageWardrobePattern.setOnClickListener {
            navigateTo(R.id.wardrobePatternDetailsToManageWardrobePattern, ManageWardrobePatternFragment.createExtras(patternId))
        }
        btnPatternElements.setOnClickListener {
            navigateTo(R.id.wardrobePatternDetailsToElementPatternGroup, ElementPatternGroupFragment.createExtras(patternId))
        }
        layoutGallery.adapter = WardrobePatternGalleryAdapter().apply {
            setItem(
                listOf(
                    "https://drive.google.com/uc?export=download&id=15lviVswk4uINm_4vO9VWQvRn4Ng6OzBT",
                    "https://drive.google.com/uc?export=download&id=15lkqr4N-dPY3Xn_Z8o1QeJFquLVVIKrf",
                    "https://drive.google.com/uc?export=download&id=15gdMb9dUftk-1UoQ_RA5q2od6G0iv4Aw",
                    "https://drive.google.com/uc?export=download&id=15lk-Z-ElRYZP3e9K8YQ1hrXJq4hkGS-T"
                )
            )
        }
    }

    companion object {

        fun createExtras(patternId: String): Bundle = Bundle().apply {
            putString(KEY_WARDROBE_PATTERN_ID, patternId)
        }
    }
}