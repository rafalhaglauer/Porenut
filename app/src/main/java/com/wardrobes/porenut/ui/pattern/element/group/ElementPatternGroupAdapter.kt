package com.wardrobes.porenut.ui.pattern.element.group

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wardrobes.porenut.R
import com.wardrobes.porenut.ui.common.extension.inflate
import kotlinx.android.synthetic.main.element_pattern_list_adapter.view.*

class ElementPatternGroupAdapter(
    private val onItemSelected: (ElementPatternViewEntity) -> Unit,
    private val onShowDrillings: (ElementPatternViewEntity) -> Unit
) : RecyclerView.Adapter<ElementPatternGroupAdapter.ViewHolder>() {
    private val items: MutableList<ElementPatternViewEntity> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.element_pattern_list_adapter))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], onItemSelected, onShowDrillings)
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<ElementPatternViewEntity>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            item: ElementPatternViewEntity,
            onItemSelected: (ElementPatternViewEntity) -> Unit,
            onShowDrillings: (ElementPatternViewEntity) -> Unit
        ) {
            with(itemView) {
                txtElementPatternName.text = item.name
                btnManageElementPattern.setOnClickListener { onItemSelected(item) }
                btnDrillingPatterns.setOnClickListener { onShowDrillings(item) }
                setOnClickListener { onItemSelected(item) }
            }
        }
    }
}