package com.wardrobes.porenut.ui.wardrobe.group.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wardrobes.porenut.R
import com.wardrobes.porenut.common.model.replace
import com.wardrobes.porenut.model.Wardrobe
import com.wardrobes.porenut.ui.wardrobe.group.model.WardrobeGroupViewModel
import kotlinx.android.synthetic.main.fragment_tab.*

class WardrobeGroupFragment : Fragment() {
    private lateinit var wardrobeGroupViewModel: WardrobeGroupViewModel

    private val wardrobes: MutableList<Wardrobe> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListView()
        observeViewModel()
    }

    private fun setupListView() {
        contentLayout.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = WardrobeGroupAdapter(wardrobes, { Log.e("wardrobe", "clicked") })
        }
    }

    private fun observeViewModel() {
        wardrobeGroupViewModel = ViewModelProviders.of(activity!!)[WardrobeGroupViewModel::class.java]
        wardrobeGroupViewModel.wardrobeGroup.observe(this, Observer {
            it?.also {
                wardrobes.replace(it)
                contentLayout.adapter.notifyDataSetChanged()
            }
        })
    }
}