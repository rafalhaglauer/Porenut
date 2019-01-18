package com.wardrobes.porenut.ui.composition.detail

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.extension.inflate
import kotlinx.android.synthetic.main.relative_drilling_list_adapter.view.*

class RelativeDrillingGroupAdapter(private val onItemSelected: (Long) -> Unit) : RecyclerView.Adapter<RelativeDrillingGroupAdapter.ViewHolder>() {
    private val drillingNames: MutableList<RelativeDrillingGroupViewEntity> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.relative_drilling_list_adapter))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(drillingNames[position], onItemSelected)
    }

    override fun getItemCount(): Int = drillingNames.size

    fun setItems(drillingNames: List<RelativeDrillingGroupViewEntity>) {
        this.drillingNames.clear()
        this.drillingNames.addAll(drillingNames)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: RelativeDrillingGroupViewEntity, onItemSelected: (Long) -> Unit): View = itemView.apply {
            txtRelativeDrillingName.text = item.name
            setOnClickListener { onItemSelected(item.id) }
        }
    }
}