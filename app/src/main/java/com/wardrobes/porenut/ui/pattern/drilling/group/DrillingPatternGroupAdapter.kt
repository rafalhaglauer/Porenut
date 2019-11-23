package com.wardrobes.porenut.ui.pattern.drilling.group

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.common.extension.inflate
import kotlinx.android.synthetic.main.drilling_pattern_list_adapter.view.*

class DrillingPatternGroupAdapter(
    private val onItemSelected: (DrillingPatternViewEntity) -> Unit
) : RecyclerView.Adapter<DrillingPatternGroupAdapter.ViewHolder>() {
    private val items: MutableList<DrillingPatternViewEntity> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.drilling_pattern_list_adapter))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], onItemSelected)
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<DrillingPatternViewEntity>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: DrillingPatternViewEntity, onItemSelected: (DrillingPatternViewEntity) -> Unit) {
            with(itemView) {
                txtDrillingPatternName.text = item.name
                btnManageDrillingPattern.setOnClickListener { onItemSelected(item) }
                btnDrillingPatterns.setOnClickListener { } // TODO!
                setOnClickListener { onItemSelected(item) }
            }
        }
    }
}