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
import android.widget.Toast
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.extension.setVisible
import com.wardrobes.porenut.ui.wardrobe.group.model.WardrobeGroupViewModel
import com.wardrobes.porenut.ui.wardrobe.group.model.WardrobeViewEntity
import kotlinx.android.synthetic.main.fragment_tab.*

class WardrobeGroupFragment : Fragment() {
    private lateinit var wardrobeGroupViewModel: WardrobeGroupViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        wardrobeGroupViewModel = ViewModelProviders.of(activity!!)[WardrobeGroupViewModel::class.java]
        observeViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }

    private fun observeViewModel() {
        wardrobeGroupViewModel.viewState
                .observe(this, Observer {
                    progress.setVisible(it!!.isLoading)
                    bindViewEntities(it.viewEntity)
                    if (it.errorMessage.isNotEmpty()) Toast.makeText(context, it.errorMessage, Toast.LENGTH_LONG).show()
                })
    }

    private fun bindViewEntities(viewEntities: List<WardrobeViewEntity>) {
        contentLayout.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = WardrobeGroupAdapter(viewEntities) { Log.e("wardrobe", "clicked") }
        }
    }
}