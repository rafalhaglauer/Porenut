package com.wardrobes.porenut.ui.composition.group

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wardrobes.porenut.R
import com.wardrobes.porenut.domain.RelativeDrillingSet
import com.wardrobes.porenut.ui.common.extension.inflate
import kotlinx.android.synthetic.main.relative_composition_list_adapter.view.*

class RelativeDrillingSetGroupAdapter(private val onItemSelected: (Long) -> Unit) : RecyclerView.Adapter<RelativeDrillingSetGroupAdapter.ViewHolder>() {

    private val drillingSets = mutableListOf<RelativeDrillingSet>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.relative_composition_list_adapter))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(drillingSets[position], onItemSelected)
    }

    override fun getItemCount(): Int = drillingSets.size

    fun setItems(drillingSets: List<RelativeDrillingSet>) {
        this.drillingSets.clear()
        this.drillingSets.addAll(drillingSets)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: RelativeDrillingSet, onItemSelected: (Long) -> Unit): View = itemView.apply {
            txtRelativeCompositionName.text = item.name
            setOnClickListener { item.id?.also(onItemSelected) }
        }
    }
}