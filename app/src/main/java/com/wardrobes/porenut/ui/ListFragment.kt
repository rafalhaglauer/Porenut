package com.wardrobes.porenut.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wardrobes.porenut.R
import com.wardrobes.porenut.model.Wardrobe
import com.wardrobes.porenut.model.WardrobeType
import kotlinx.android.synthetic.main.fragment_tab.*
import java.util.*


class ListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val wardrobeList = Arrays.asList(Wardrobe("DSG", 600f, 700f, 520f, WardrobeType.HANGING), Wardrobe("DSG", 600f, 700f, 520f, WardrobeType.HANGING),
                Wardrobe("DSG", 600f, 700f, 520f, WardrobeType.HANGING), Wardrobe("DSG", 600f, 700f, 520f, WardrobeType.HANGING),
                Wardrobe("DSG", 600f, 700f, 520f, WardrobeType.HANGING), Wardrobe("DSG", 600f, 700f, 520f, WardrobeType.HANGING),
                Wardrobe("DSG", 600f, 700f, 520f, WardrobeType.HANGING), Wardrobe("DSG", 600f, 700f, 520f, WardrobeType.HANGING))

        contentLayout.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = WardrobeGroupAdapter(wardrobeList, { Log.e("wardrobe", "clicked") })
        }
    }
}