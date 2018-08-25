package com.wardrobes.porenut.ui.relative.drilling

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.extension.inflate
import kotlinx.android.synthetic.main.relative_drilling_list_adapter.view.*


class RelativeDrillingGroupAdapter(
    private val drillingNames: List<String>,
    private val onItemSelected: (String) -> Unit
) :
    RecyclerView.Adapter<RelativeDrillingGroupAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.relative_drilling_list_adapter))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(drillingNames[position], onItemSelected)
    }

    override fun getItemCount(): Int = drillingNames.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: String, onItemSelected: (String) -> Unit): View = itemView.apply {
            txtRelativeDrillingName.text = item
            setOnClickListener { onItemSelected(item) }
        }
    }
}